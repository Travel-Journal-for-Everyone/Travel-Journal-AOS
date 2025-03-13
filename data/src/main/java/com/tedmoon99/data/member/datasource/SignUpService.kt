package com.tedmoon99.data.member.datasource

import com.tedmoon99.data.member.model.DuplicatedCheckResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SignUpService {

    @GET("v1/member/check-nickname/{nickname}")
    suspend fun checkDuplicatedName(
        @Path("nickname") name: String,
    ): Response<DuplicatedCheckResponse>
}