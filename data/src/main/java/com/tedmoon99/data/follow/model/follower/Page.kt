package com.tedmoon99.data.follow.model.follower


import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("number")
    val number: Long,
    @SerializedName("size")
    val size: Long,
    @SerializedName("totalElements")
    val totalElements: Long,
    @SerializedName("totalPages")
    val totalPages: Long
)