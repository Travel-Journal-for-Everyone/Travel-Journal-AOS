package com.tedmoon99.domain.search.respository

import androidx.paging.PagingData
import com.tedmoon99.domain.search.model.SearchResultItem
import com.tedmoon99.domain.search.utils.SearchTabType
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getSearchedUserResult(keyword: String, searchTabType: SearchTabType): Flow<PagingData<SearchResultItem>>

    suspend fun getAllKeyword(): List<String>

    suspend fun setRecentSearchKeyword(keyword: String)

    suspend fun removeRecentSearchKeyword(keyword: String)

    suspend fun removeAll()
}