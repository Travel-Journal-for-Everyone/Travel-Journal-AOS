package com.tedmoon99.data.member.model


import com.google.gson.annotations.SerializedName

data class DuplicatedCheckResponse(
    @SerializedName("data")
    val data: String?,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)