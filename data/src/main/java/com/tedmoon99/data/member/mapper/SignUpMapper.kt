package com.tedmoon99.data.member.mapper

import com.tedmoon99.data.member.model.SignUpDto
import com.tedmoon99.data.member.model.SignUpResult
import com.tedmoon99.domain.member.entity.SignUpDtoEntity
import com.tedmoon99.domain.member.entity.SignUpResultEntity

object SignUpMapper {

    fun fromDomain(domain: SignUpDtoEntity): SignUpDto {
        return SignUpDto(
            nickname = domain.nickname,
            accountScope = domain.accountScope
        )
    }

    fun toDomain(data: SignUpDto): SignUpDtoEntity {
        return SignUpDtoEntity(
            nickname = data.nickname,
            accountScope = data.accountScope
        )
    }

    fun resultFromDomain(domain: SignUpResultEntity): SignUpResult {
        return SignUpResult(
            success = domain.success,
            message = domain.message
        )
    }

    fun resultToDomain(data: SignUpResult): SignUpResultEntity {
        return SignUpResultEntity(
            success = data.success,
            message = data.message
        )
    }
}