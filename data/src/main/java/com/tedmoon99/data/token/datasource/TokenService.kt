package com.tedmoon99.data.token.datasource

import com.tedmoon99.data.token.model.RefreshToken
import com.tedmoon99.data.token.model.RefreshTokenDto
import com.tedmoon99.data.token.model.TokenApiResponse
import retrofit2.Response
import retrofit2.http.POST

interface TokenService {

    @POST("v1/tokens/reissue")
    suspend fun refreshToken(request: RefreshTokenDto): Response<TokenApiResponse<RefreshToken>>

}