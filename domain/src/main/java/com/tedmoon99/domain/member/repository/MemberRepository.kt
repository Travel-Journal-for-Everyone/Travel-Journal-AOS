package com.tedmoon99.domain.member.repository

interface MemberRepository {

    suspend fun setUserId(userId: Int)

    suspend fun getUserId(): Int?
}