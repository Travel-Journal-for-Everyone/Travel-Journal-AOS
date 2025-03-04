package com.tedmoon99.data.token.model

data class RefreshTokenDto(
    val refreshToken: String,
    val deviceId: String,
) {
}