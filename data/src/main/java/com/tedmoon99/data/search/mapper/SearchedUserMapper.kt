package com.tedmoon99.data.search.mapper

import com.tedmoon99.data.search.model.user.User
import com.tedmoon99.domain.search.model.SearchedUser

object SearchedUserMapper {
    fun fromDomain(domain: SearchedUser): User {
        TODO("Not yet implemented")
    }

    fun toDomain(data: User): SearchedUser {
        return SearchedUser(
            memberId = data.memberId,
            nickName = data.nickname,
            profileImageUrl = data.profileImageUrl,
            travelJournalCount = data.travelDiaryCount,
            placesCount = data.placesCount
        )
    }
}