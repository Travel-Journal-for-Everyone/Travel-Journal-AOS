package com.tedmoon99.data.follow.mapper

import com.tedmoon99.data.follow.model.follower.FollowerEntity
import com.tedmoon99.domain.follow.model.Follower


object FollowerMapper {
    fun toDomain(data: FollowerEntity): Follower {
        return Follower(
            memberId = data.memberId,
            nickName = data.nickname,
            placeCount = data.placesCount,
            profileImageUrl = data.profileImageUrl,
            diaryCount = data.travelDiaryCount
        )

    }
}