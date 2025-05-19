package com.tedmoon99.data.follow.model.utils.request


import com.google.gson.annotations.SerializedName

data class FollowRequestDto(
    @SerializedName("content")
    val followRequestEntities: List<FollowRequestEntity>,
    @SerializedName("page")
    val page: Page
)