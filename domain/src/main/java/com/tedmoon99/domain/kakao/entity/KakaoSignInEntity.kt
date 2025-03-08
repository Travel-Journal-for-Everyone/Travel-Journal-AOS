package com.tedmoon99.domain.kakao.entity

data class KakaoSignInEntity(
    val memberId: Int,
    val isFirstLogin: Boolean,
    val refreshToken: String,
    val deviceId: String
)