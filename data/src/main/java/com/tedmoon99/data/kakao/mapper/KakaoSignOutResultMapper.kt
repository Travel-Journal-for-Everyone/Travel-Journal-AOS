package com.tedmoon99.data.kakao.mapper

import com.tedmoon99.data.kakao.model.KakaoSignOutResult
import com.tedmoon99.domain.kakao.entity.KakaoSignOutResultEntity

object KakaoSignOutResultMapper {

    fun fromDomain(domain: KakaoSignOutResultEntity): KakaoSignOutResult {
        return KakaoSignOutResult(
            success = domain.success,
            errorMessage = domain.errorMessage
        )
    }

    fun toDomain(data: KakaoSignOutResult): KakaoSignOutResultEntity {
        return KakaoSignOutResultEntity(
            success = data.success,
            errorMessage = data.errorMessage
        )
    }
}