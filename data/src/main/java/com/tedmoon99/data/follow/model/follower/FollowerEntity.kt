package com.tedmoon99.data.follow.model.follower


import com.google.gson.annotations.SerializedName

data class FollowerEntity(
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