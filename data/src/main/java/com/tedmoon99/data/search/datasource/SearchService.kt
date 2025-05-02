package com.tedmoon99.data.search.datasource

import com.tedmoon99.data.search.model.user.SearchUserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    // 사용자 검색
    @GET("v1/search/members")
    suspend fun searchUser(
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
    ): Response<SearchUserDto>
}