package com.tedmoon99.data.follow.model.following


import com.google.gson.annotations.SerializedName

data class FollowingDto(
    @SerializedName("content")
    val followingEntities: List<FollowingEntity>,
    @SerializedName("page")
    val page: Page
)