package com.tedmoon99.data.follow.mapper

import com.tedmoon99.data.follow.model.following.FollowingEntity
import com.tedmoon99.domain.follow.model.Following

object FollowingMapper {

    fun toDomain(data: FollowingEntity): Following {
        return Following(
            memberId = data.memberId,
            nickName = data.nickname,
            profileImageUrl = data.profileImageUrl,
            diaryCount = data.travelDiaryCount,
            placeCount = data.placesCount
        )
    }
}