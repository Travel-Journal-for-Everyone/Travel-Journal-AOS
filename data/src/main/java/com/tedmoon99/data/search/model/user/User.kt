package com.tedmoon99.data.search.model.user


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("placesCount")
    val placesCount: Int,
    @SerializedName("profileImageUrl")
    val profileImageUrl: String,
    @SerializedName("travelDiaryCount")
    val travelDiaryCount: Int
)