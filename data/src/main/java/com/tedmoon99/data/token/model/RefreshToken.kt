package com.tedmoon99.data.token.model

import com.google.gson.annotations.SerializedName

data class RefreshToken(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("email")
    val memberEmail: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("deviceId")
    val deviceId: String,
)
