package com.tedmoon99.data.token.datasource

import android.util.Log
import com.tedmoon99.domain.token.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        // Access 토큰 조회
        val token = runBlocking { tokenRepository.getAccessToken() ?: "" }
        Log.d(TAG, "Access 토큰 조회: $token")

        // 요청에 Access 토큰 추가
        val newRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        // 요청 전송
        return chain.proceed(newRequest)
    }

    companion object {
        private const val TAG = "TokenInterceptor"
    }
}