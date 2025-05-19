package com.tedmoon99.data.follow.model.utils

import com.google.gson.annotations.SerializedName

data class FollowCountDto(
    @SerializedName("followings")
    val following: Int,
    @SerializedName("followerEntities")
    val follower: Int,
)
