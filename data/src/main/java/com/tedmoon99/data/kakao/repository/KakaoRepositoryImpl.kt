package com.tedmoon99.data.kakao.repository

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.tedmoon99.data.kakao.datasource.KakaoService
import com.tedmoon99.data.kakao.mapper.KakaoSignInResultMapper
import com.tedmoon99.data.kakao.mapper.KakaoSignOutResultMapper
import com.tedmoon99.data.kakao.model.KakaoSignInResult
import com.tedmoon99.data.kakao.model.KakaoSignOutResult
import com.tedmoon99.domain.kakao.entity.KakaoSignInResultEntity
import com.tedmoon99.domain.kakao.entity.KakaoSignOutResultEntity
import com.tedmoon99.domain.kakao.repository.KakaoRepository
import com.tedmoon99.domain.member.repository.MemberRepository
import com.tedmoon99.domain.token.repository.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoRepositoryImpl @Inject constructor(
    private val context: Context,
    private val kakaoService: KakaoService,
    private val tokenRepository: TokenRepository,
    private val memberRepository: MemberRepository,

    ) : KakaoRepository {
    private val _isKakaoSignIn = MutableStateFlow(false)
    private val isKakaoSignIn: StateFlow<Boolean> = _isKakaoSignIn

    override fun isKakaoSignIn(): Boolean {
        return isKakaoSignIn.value
    }

    override suspend fun requestKakaoSignIn(): KakaoSignInResultEntity {
        return if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            // 카카오톡으로 로그인
            val token = suspendCoroutine { continuation ->
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)
                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context) { accountToken, accountError ->
                            if (accountError != null) {
                                Log.e(TAG, "카카오계정으로 로그인 실패", accountError)
                                continuation.resumeWithException(accountError)
                            } else if (accountToken != null) {
                                Log.i(TAG, "카카오계정으로 로그인 성공 ${accountToken.accessToken}")
                                continuation.resume(accountToken)
                            }
                        }
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                        continuation.resume(token)
                    }
                }
            }

            val result = sendSignInRequest(token)
            KakaoSignInResultMapper.toDomain(result)
        } else {
            val result = requestKakaoAccountLogin()
            KakaoSignInResultMapper.toDomain(result)
        }
    }

    private suspend fun requestKakaoAccountLogin(): KakaoSignInResult {
        return try {
            // 카카오 계정 로그인
            val token = suspendCoroutine { continuation ->
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오계정으로 로그인 실패", error)
                        continuation.resumeWithException(error)
                    } else if (token != null) {
                        Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                        continuation.resume(token)
                    }
                }
            }
            // 로그인 성공 후 서버 요청
            sendSignInRequest(token)
        } catch (error: Throwable) {
            Log.e(TAG, "카카오 로그인 과정 중 오류 발생", error)
            KakaoSignInResult(success = false)
        }
    }

    override suspend fun requestKakaoSignOut(): KakaoSignOutResultEntity {
        return try {
            val result = suspendCoroutine { continuation ->
                // 카카오 플랫폼 서버에 카카오 로그아웃 요청
                UserApiClient.instance.logout { error: Throwable? ->
                    if (error != null) {
                        Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                        continuation.resumeWithException(error)
                    } else {
                        Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                        continuation.resume(true)
                    }
                }
            }
            // 카카오 로그인 상태 업데이트
            _isKakaoSignIn.value = result
            Log.i(TAG, "카카오 로그아웃 상태: ${result}")

            val signOutResult = KakaoSignOutResult(success = result)

            KakaoSignOutResultMapper.toDomain(signOutResult)
        } catch (e: Throwable) {
            Log.e(TAG, "카카오 로그아웃 과정 중 오류 발생", e)
            val signOutResult = KakaoSignOutResult(success = false, errorMessage = "${e.message}")
            KakaoSignOutResultMapper.toDomain(signOutResult)
        }
    }

    // 서버에 카카오 로그인 정보를 전달
    // 사용자 정보 저장
    private suspend fun sendSignInRequest(token: OAuthToken): KakaoSignInResult {

        val kakaoIdToken = token.idToken ?: ""
        // 서버에 카카오에서 받아온 deviceId 전달
        val response = kakaoService.requestKakaoSignIn(deviceId = kakaoIdToken)

        return if (response.isSuccessful && response.code() == 200) {

            val accessToken = response.headers()["Authorization"]?.removePrefix("Bearer ") ?: ""
            val responseBody = response.body()

            responseBody?.let {
                // 유저 정보 저장
                memberRepository.run {
                    setUserId(it.memberId)
                }
                // 토큰 정보 저장
                tokenRepository.run {
                    setAccessToken(accessToken)
                    setRefreshToken(it.refreshToken)
                    setDeviceId(it.deviceId)
                }
                // 카카오 로그인 상태 업데이트
                _isKakaoSignIn.value = true
                Log.i(TAG, "카카오 로그인 상태: ${true}")

                KakaoSignInResult(success = true, isFirstLogin = it.isFirstLogin)
            } ?: KakaoSignInResult(success = false)
        } else {
            Log.e(TAG, "카카오 로그인 오류 코드: ${response.code()}")
            KakaoSignInResult(success = false)
        }
    }

    companion object {
        private const val TAG = "KakaoRepositoryImpl"
    }
}