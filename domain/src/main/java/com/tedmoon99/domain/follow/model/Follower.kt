package com.tedmoon99.domain.follow.model

import com.tedmoon99.domain.follow.utils.FollowListItem

data class Follower(
    override val memberId:Int,
    override val nickName:String,
    override val profileImageUrl: String,
    override val diaryCount: Int,
    override val placeCount: Int,
): FollowListItem
