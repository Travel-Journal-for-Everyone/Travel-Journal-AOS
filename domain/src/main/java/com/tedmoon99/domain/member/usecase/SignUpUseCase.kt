package com.tedmoon99.domain.member.usecase

import com.tedmoon99.domain.member.entity.DuplicatedCheckResultEntity
import com.tedmoon99.domain.member.entity.SignUpResultEntity

interface SignUpUseCase {

    suspend fun checkDuplicatedName(name: String): DuplicatedCheckResultEntity

    suspend fun completeSignUp(name: String, accountScope: String): SignUpResultEntity

}