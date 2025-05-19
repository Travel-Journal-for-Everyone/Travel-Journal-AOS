package com.tedmoon99.data.follow.datasource

import com.tedmoon99.data.follow.model.follower.FollowerDto
import com.tedmoon99.data.follow.model.following.FollowingDto
import com.tedmoon99.data.follow.model.utils.FollowCountDto
import com.tedmoon99.data.follow.model.utils.request.FollowRequestDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FollowService {

    // 현재 로그인 회원이 특정 회원을 팔로우
    @POST("v1/follow/{memberId}")
    suspend fun follow(
        @Path("memberId") memberId: Int,
    ): Response<Unit>

    // 현재 로그인 회원이 특정 회원을 언팔로우
    @DELETE("v1/follow/{memberId}")
    suspend fun unfollow(
        @Path("memberId") memberId: Int,
    ): Response<Unit>

    // 팔로우 요청 거절
    @POST("v1/follow/requestEntities/{followId}/reject")
    suspend fun denyFollowRequest(
        @Path("followId") followId: Int,
    ): Response<Unit>

    // 팔로우 요청 수락
    @POST("v1/follow/requestEntities/{followId}/accept")
    suspend fun approveFollowRequest(
        @Path("followId") followId: Int,
    ): Response<Unit>

    // 팔로우 여부 확인
    @GET("v1/follow/{memberId}/is-following")
    suspend fun isFollowing(
        @Path("memberId") memberId: Int,
    ): Response<Boolean>

    // 특정 회원의 팔로잉 목록
    @GET("v1/follow/{memberId}/followings")
    suspend fun getFollowingList(
        @Path("memberId") memberId: Int,
        @Query("page") page: Int,
    ): Response<FollowingDto>

    // 특정 회원의 팔로워 목록
    @GET("v1/follow/{memberId}/followerEntities")
    suspend fun getFollowerList(
        @Path("memberId") memberId: Int,
        @Query("page") page: Int,
    ): Response<FollowerDto>

    // 팔로우 수 및 팔로워 수 조회
    @GET("v1/follow/{memberId}/count")
    suspend fun getFollowCount(
        @Path("memberId") memberId: Int,
    ): Response<FollowCountDto>

    // 현재 로그인 회원 팔로우 요청 목록
    @GET("v1/follow/request")
    suspend fun getFollowRequestList(
        @Query("page") page: Int,
    ): Response<FollowRequestDto>

}