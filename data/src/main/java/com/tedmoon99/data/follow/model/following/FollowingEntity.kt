package com.tedmoon99.data.follow.model.following


import com.google.gson.annotations.SerializedName

data class FollowingEntity(
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