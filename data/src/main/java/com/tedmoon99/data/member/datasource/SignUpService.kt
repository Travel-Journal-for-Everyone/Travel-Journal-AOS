package com.tedmoon99.data.member.datasource

import com.tedmoon99.data.member.model.SignUpDto
import com.tedmoon99.data.member.model.SignUpResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SignUpService {

    @GET("v1/member/check-nickname/{nickname}")
    suspend fun checkDuplicatedName(
        @Path("nickname") name: String,
    ): Response<Unit>

    @POST("v1/member/complete-first-login")
    suspend fun completeSignUp(
        @Body request: SignUpDto
    ): Response<SignUpResult>

}