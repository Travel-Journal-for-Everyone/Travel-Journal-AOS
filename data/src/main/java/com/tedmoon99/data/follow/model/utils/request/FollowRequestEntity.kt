package com.tedmoon99.data.follow.model.utils.request


import com.google.gson.annotations.SerializedName

data class FollowRequestEntity(
    @SerializedName("followId")
    val followId: Int,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("placesCount")
    val placesCount: Int,
    @SerializedName("profileImageUrl")
    val profileImageUrl: String,
    @SerializedName("requestStatus")
    val requestStatus: String,
    @SerializedName("travelDiaryCount")
    val travelDiaryCount: Int
)