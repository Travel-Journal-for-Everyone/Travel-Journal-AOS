package com.tedmoon99.data.follow.mapper

import com.tedmoon99.data.follow.model.utils.FollowCountDto
import com.tedmoon99.domain.follow.model.FollowCount

object FollowCountMapper {

    fun toDomain(data: FollowCountDto): FollowCount {
        return FollowCount(
            following = data.following,
            follower = data.follower
        )
    }
}