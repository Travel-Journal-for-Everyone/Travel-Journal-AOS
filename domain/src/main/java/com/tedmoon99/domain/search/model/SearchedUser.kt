package com.tedmoon99.domain.search.model

data class SearchedUser(
    val memberId: Int,
    val nickName: String,
    val profileImageUrl: String,
    val travelJournalCount: Int,
    val placesCount: Int,
): SearchResultItem
