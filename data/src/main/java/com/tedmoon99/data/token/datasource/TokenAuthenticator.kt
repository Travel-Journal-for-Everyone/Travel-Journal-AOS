package com.tedmoon99.data.token.datasource

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.tedmoon99.data.token.model.RefreshTokenDto
import com.tedmoon99.domain.token.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenService: TokenService,
    private val tokenRepository: TokenRepository,
): Authenticator {
    // 중복 실행 방지
    private var mutex = Mutex()
    // 401 UnAuthorization 발생 시 자동 호출
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 3) {
            Log.e(TAG, "토큰 재발행 실패 3회 이상 -> 강제 로그아웃")
            return null
        }

        if (mutex.isLocked) return null

        val currentTime = System.currentTimeMillis()
        if ((currentTime - lastRefreshTime) < MIN_REFRESH_INTERVAL) return null

        response.request.header("Authorization") ?: return null

        return runBlocking(Dispatchers.IO) {
            mutex.withLock {
                try {
                    val newToken = requestRefreshToken() ?: ""
                    Log.d(TAG, "새로운 Access 토큰 : ${newToken}")

                    response.request.newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()
                } catch (error: HttpException) {
                    Log.e(TAG, "토큰 재발급 실패01 : ${error.message}")
                    null
                }
            }
        }
    }

    private suspend fun requestRefreshToken(): String? {
        return try {
            val oldToken = tokenRepository.getRefreshToken() ?: ""
            val deviceId = tokenRepository.getDeviceId() ?: ""
            val refreshTokenDto = RefreshTokenDto(refreshToken = oldToken, deviceId = deviceId)
            Log.i(TAG, "$refreshTokenDto")
            val response = runBlocking { tokenService.refreshToken(refreshTokenDto) }
            Log.i(TAG, "Refresh 토큰 조회 상태 코드 : ${response.code()}")
            val responseBody = response.body()

            // 요청 실패
            if (!response.isSuccessful) {
                Log.e(TAG, "토큰 재발행 실패02 : ${response.errorBody()?.string()}")
                return null
            }

            responseBody?.error?.let { error ->
                Log.e(TAG, "토큰 갱신 실패: ${error.code}-${error.message}")
                handleRefreshTokenError(error.code)
                return null
            }

            val newToken = response.headers()["Authorization"]?.removePrefix("Bearer ")
            // Access 토큰, Refresh 토큰, DeviceId 저장
            newToken?.let {
                tokenRepository.setAccessToken(it)
                responseBody?.data?.refreshToken?.let { newRefreshToken ->
                    tokenRepository.setRefreshToken(newRefreshToken)
                }
                responseBody?.data?.deviceId?.let { newDeviceId ->
                    tokenRepository.setDeviceId(newDeviceId)
                }
            }
            newToken
        } catch (error: Exception) {
            Log.e(TAG, "토큰 재발급 중 오류 발생: ${error.message}")
            null
        }
    }

    private fun handleRefreshTokenError(errorCode: Int){
        when (errorCode) {

            else -> {
                Log.e(TAG, "예상치 못한 오류 발생")
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 0
        var currentResponse: Response? = response
        while (currentResponse != null) {
            count++
            currentResponse = currentResponse.priorResponse
        }
        return count
    }

    companion object {
        private const val TAG = "TokenAuthenticator"
        private const val MIN_REFRESH_INTERVAL = 5000L // 5초 제한
        private var lastRefreshTime = 0L
    }
}