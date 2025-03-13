package com.tedmoon99.domain.member.usecase

import com.tedmoon99.domain.member.entity.DuplicatedCheckResultEntity
import com.tedmoon99.domain.member.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val signUpRepository: SignUpRepository
): SignUpUseCase {

    override suspend fun checkDuplicatedName(name: String): DuplicatedCheckResultEntity {
        return signUpRepository.checkDuplicatedName(name)
    }
}