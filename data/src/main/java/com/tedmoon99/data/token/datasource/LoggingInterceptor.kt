package com.tedmoon99.data.token.datasource

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val startTime = System.nanoTime()

        startRequestLog(chain)

        val response = chain.proceed(request)

        val endTime = System.nanoTime()

        endRequestLog(response, startTime, endTime)

        return response
    }


    companion object {
        private const val TAG = "LoggingInterceptor"

        private fun startRequestLog(chain: Interceptor.Chain){
            val request = chain.request()
            Log.i(TAG,"요청 전송 url: ${request.url}\n" +
                    "연결 상태: ${chain.connection()}\n" +
                    "AccessToken: ${request.header("Authorization")}")
        }

        private fun endRequestLog(response: Response, startTime: Long, endTime: Long){
            val request = response.request
            Log.i(TAG,"요청 결과 url: ${request.url}\n" +
                    "응답 시간: ${(endTime-startTime) / 1000000}\n" +
                    "AccessToken: ${request.header("Authorization")}")
        }
    }

}