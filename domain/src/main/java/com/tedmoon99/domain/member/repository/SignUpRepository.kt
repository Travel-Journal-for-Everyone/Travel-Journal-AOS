package com.tedmoon99.domain.member.repository

import com.tedmoon99.domain.member.entity.DuplicatedCheckResultEntity

interface SignUpRepository {

    suspend fun checkDuplicatedName(name: String): DuplicatedCheckResultEntity
}