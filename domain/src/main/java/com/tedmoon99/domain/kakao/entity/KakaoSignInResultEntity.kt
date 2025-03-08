package com.tedmoon99.domain.kakao.entity

data class KakaoSignInResultEntity(
    val success: Boolean,
    val isFirstLogin: Boolean = false
)
