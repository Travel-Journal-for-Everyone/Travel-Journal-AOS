package com.tedmoon99.domain.search.usecase

import androidx.paging.PagingData
import com.tedmoon99.domain.search.model.SearchResultItem
import com.tedmoon99.domain.search.utils.SearchTabType
import kotlinx.coroutines.flow.Flow

interface SearchUseCase {

    suspend fun getItems(keyword: String, tab: SearchTabType): Flow<PagingData<SearchResultItem>>
}