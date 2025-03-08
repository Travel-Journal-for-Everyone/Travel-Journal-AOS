package com.tedmoon99.data.kakao.datasource

import com.tedmoon99.data.kakao.model.KakaoSignIn
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface KakaoService {
    // Kakao SignIn
    @POST("v1/auth/kakao/id-token-login/{deviceId}")
    suspend fun requestKakaoSignIn(
        @Query("deviceId") deviceId: String,
    ): Response<KakaoSignIn>

}