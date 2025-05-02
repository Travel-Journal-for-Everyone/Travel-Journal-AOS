package com.tedmoon99.domain.search.usecase

import androidx.paging.PagingData
import com.tedmoon99.domain.search.model.SearchResultItem
import com.tedmoon99.domain.search.respository.SearchRepository
import com.tedmoon99.domain.search.utils.SearchTabType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository,
) : SearchUseCase {

    override suspend fun getItems(
        keyword: String,
        tab: SearchTabType
    ): Flow<PagingData<SearchResultItem>> {
        return searchRepository.getSearchedUserResult(keyword, tab)
    }
}