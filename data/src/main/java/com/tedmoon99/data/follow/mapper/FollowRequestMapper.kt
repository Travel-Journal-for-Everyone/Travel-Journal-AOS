package com.tedmoon99.data.follow.mapper

import com.tedmoon99.data.follow.model.utils.request.FollowRequestEntity
import com.tedmoon99.domain.follow.model.FollowRequest

object FollowRequestMapper {

    fun toDomain(data: FollowRequestEntity): FollowRequest {
        return FollowRequest(
            followId = data.followId,
            memberId = data.memberId,
            nickName = data.nickname,
            profileImageUrl = data.profileImageUrl,
            diaryCount = data.travelDiaryCount,
            placeCount = data.placesCount,
            requestStatus = data.requestStatus,
        )
    }
}