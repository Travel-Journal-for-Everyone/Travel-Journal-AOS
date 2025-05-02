package com.tedmoon99.domain.search.usecase

interface SearchKeywordUseCase {

    suspend fun getAllKeyword(): List<String>

    suspend fun setRecentSearchKeyword(keyword: String)

    suspend fun removeRecentSearchKeyword(keyword: String)

    suspend fun removeAll()

}