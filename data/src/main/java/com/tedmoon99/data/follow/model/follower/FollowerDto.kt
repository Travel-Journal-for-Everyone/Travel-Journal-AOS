package com.tedmoon99.data.follow.model.follower


import com.google.gson.annotations.SerializedName

data class FollowerDto(
    @SerializedName("content")
    val followerEntities: List<FollowerEntity>,
    @SerializedName("page")
    val page: Page
)