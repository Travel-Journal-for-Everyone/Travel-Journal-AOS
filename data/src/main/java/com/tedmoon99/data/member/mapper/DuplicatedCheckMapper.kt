package com.tedmoon99.data.member.mapper

import com.tedmoon99.data.member.model.DuplicatedCheckResult
import com.tedmoon99.domain.member.entity.DuplicatedCheckResultEntity

object DuplicatedCheckMapper {

    fun fromDomain(domain: DuplicatedCheckResultEntity): DuplicatedCheckResult {
        return DuplicatedCheckResult(
            success = domain.success,
            isDuplicated = domain.isDuplicated,
            isBadWorld = domain.isBadWorld
        )
    }

    fun toDomain(data: DuplicatedCheckResult): DuplicatedCheckResultEntity {
        return DuplicatedCheckResultEntity(
            success = data.success,
            isDuplicated = data.isDuplicated,
            isBadWorld = data.isBadWorld
        )
    }
}