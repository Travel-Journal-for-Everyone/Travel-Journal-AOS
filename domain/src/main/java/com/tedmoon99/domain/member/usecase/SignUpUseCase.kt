package com.tedmoon99.domain.member.usecase

import com.tedmoon99.domain.member.entity.DuplicatedCheckResultEntity

interface SignUpUseCase {

    suspend fun checkDuplicatedName(name: String): DuplicatedCheckResultEntity

}