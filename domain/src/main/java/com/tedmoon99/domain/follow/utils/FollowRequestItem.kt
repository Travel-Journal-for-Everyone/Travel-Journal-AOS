package com.tedmoon99.domain.follow.utils

interface FollowRequestItem {
    val memberId:Int
    val nickName:String
    val profileImageUrl: String
    val diaryCount: Int
    val placeCount: Int
}