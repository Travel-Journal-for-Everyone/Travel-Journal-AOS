package com.tedmoon99.domain.member.repository

import com.tedmoon99.domain.member.entity.DuplicatedCheckResultEntity
import com.tedmoon99.domain.member.entity.SignUpDtoEntity
import com.tedmoon99.domain.member.entity.SignUpResultEntity

interface SignUpRepository {

    suspend fun checkDuplicatedName(name: String): DuplicatedCheckResultEntity

    suspend fun completeSignUp(request: SignUpDtoEntity): SignUpResultEntity
}