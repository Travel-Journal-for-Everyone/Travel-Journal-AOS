package com.tedmoon99.data.kakao.mapper

import com.tedmoon99.data.kakao.model.KakaoSignIn
import com.tedmoon99.domain.kakao.entity.KakaoSignInEntity

object KakaoSignInMapper {

    fun fromDomain(domain: KakaoSignInEntity): KakaoSignIn {
        return KakaoSignIn(
            memberId = domain.memberId,
            isFirstLogin = domain.isFirstLogin,
            refreshToken = domain.refreshToken,
            deviceId = domain.refreshToken
        )
    }

    fun toDomain(data: KakaoSignIn): KakaoSignInEntity {
        return KakaoSignInEntity(
            memberId = data.memberId,
            isFirstLogin = data.isFirstLogin,
            refreshToken = data.refreshToken,
            deviceId = data.deviceId
        )
    }
}