package com.tedmoon99.domain.member.usecase

import com.tedmoon99.domain.member.entity.DuplicatedCheckResultEntity
import com.tedmoon99.domain.member.entity.SignUpDtoEntity
import com.tedmoon99.domain.member.entity.SignUpResultEntity
import com.tedmoon99.domain.member.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val signUpRepository: SignUpRepository
): SignUpUseCase {

    override suspend fun checkDuplicatedName(name: String): DuplicatedCheckResultEntity {
        return signUpRepository.checkDuplicatedName(name)
    }

    override suspend fun completeSignUp(name: String, accountScope: String): SignUpResultEntity {

        // DtoEntity 생성
        val dtoEntity = SignUpDtoEntity(nickname = name, accountScope = accountScope)
        // api 호출
        return signUpRepository.completeSignUp(dtoEntity)
    }
}