package com.tedmoon99.domain.search.usecase

import com.tedmoon99.domain.search.respository.SearchRepository
import javax.inject.Inject

class SearchKeywordUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository,
): SearchKeywordUseCase {
    override suspend fun getAllKeyword(): List<String> = searchRepository.getAllKeyword()

    override suspend fun setRecentSearchKeyword(keyword: String) = searchRepository.setRecentSearchKeyword(keyword)

    override suspend fun removeRecentSearchKeyword(keyword: String) = searchRepository.removeRecentSearchKeyword(keyword)

    override suspend fun removeAll() = searchRepository.removeAll()
}