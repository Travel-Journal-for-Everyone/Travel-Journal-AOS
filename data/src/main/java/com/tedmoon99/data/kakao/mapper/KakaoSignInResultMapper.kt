package com.tedmoon99.data.kakao.mapper

import com.tedmoon99.data.kakao.model.KakaoSignInResult
import com.tedmoon99.domain.kakao.entity.KakaoSignInResultEntity

object KakaoSignInResultMapper {

    fun fromDomain(domain: KakaoSignInResultEntity): KakaoSignInResult {
        return KakaoSignInResult(
            success = domain.success,
            isFirstLogin = domain.isFirstLogin
        )
    }

    fun toDomain(data: KakaoSignInResult): KakaoSignInResultEntity {
        return KakaoSignInResultEntity(
            success = data.success,
            isFirstLogin = data.isFirstLogin
        )
    }
}