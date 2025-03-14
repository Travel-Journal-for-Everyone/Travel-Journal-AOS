package com.tedmoon99.data.member.model

import com.google.gson.annotations.SerializedName

data class SignUpResult(
    @SerializedName("data")
    val data: String? = null,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
