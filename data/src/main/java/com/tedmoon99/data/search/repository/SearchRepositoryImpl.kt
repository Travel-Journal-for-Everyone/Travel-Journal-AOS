package com.tedmoon99.data.search.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tedmoon99.data.search.datasource.SearchUserPagingSource
import com.tedmoon99.data.common.utils.CommonUtils.Companion.PAGING_SIZE
import com.tedmoon99.data.search.datasource.SearchService
import com.tedmoon99.data.search.mapper.SearchedUserMapper
import com.tedmoon99.domain.search.model.SearchResultItem
import com.tedmoon99.domain.search.respository.SearchRepository
import com.tedmoon99.domain.search.utils.SearchTabType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
    private val dataStore: DataStore<Preferences>,
) : SearchRepository {
    override suspend fun getSearchedUserResult(
        keyword: String,
        searchTabType: SearchTabType
    ): Flow<PagingData<SearchResultItem>> {

        val pagingConfig = PagingConfig(pageSize = PAGING_SIZE, enablePlaceholders = false)

        val pagingSourceFactory = when (searchTabType){
            SearchTabType.Diary -> { SearchUserPagingSource(searchService, keyword, SearchedUserMapper) }
            SearchTabType.Place -> { SearchUserPagingSource(searchService, keyword, SearchedUserMapper) }
            SearchTabType.User -> { SearchUserPagingSource(searchService, keyword, SearchedUserMapper) }
            SearchTabType.WrongTypeSearch -> {
                Log.d(TAG, "잘못된 입력입니다")
                SearchUserPagingSource(searchService, keyword, SearchedUserMapper)
            }
        }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { pagingSourceFactory }
        ).flow
    }

    override suspend fun getAllKeyword(): List<String> {
        val currentKeywordList = dataStore.data.map { prefs ->
            prefs[RECENT_KEYWORDS_KEY]?.split(",") ?: emptyList()
        }.first()
        Log.d(TAG,"현재 저장된 로컬 검색 리스트: $currentKeywordList")
        return currentKeywordList
    }

    override suspend fun setRecentSearchKeyword(keyword: String) {
        // 최근 저장 리스트 조회
        val currentKeywordList = dataStore.data.map { prefs ->
            prefs[RECENT_KEYWORDS_KEY]?.split(",") ?: emptyList()
        }.first()
        Log.d(TAG,"현재 저장된 로컬 검색 리스트: $currentKeywordList")

        // 저장된 리스트에 추가
        val updatedList = listOf(keyword) + currentKeywordList.filterNot { it == keyword }

        Log.d(TAG,"${keyword}를 추가한 로컬 검색 리스트: $updatedList")

        // 새로운 리스트 String으로 변환 후 저장
        dataStore.edit { prefs ->
            prefs[RECENT_KEYWORDS_KEY] = updatedList.joinToString(",")
        }
    }

    override suspend fun removeRecentSearchKeyword(keyword: String) {
        // 최근 저장 리스트 조회
        val currentKeywordList = dataStore.data.map { prefs ->
            prefs[RECENT_KEYWORDS_KEY]?.split(",") ?: emptyList()
        }.first()

        Log.d(TAG,"현재 저장된 로컬 검색 리스트: $currentKeywordList")

        // 저장된 리스트에서 제거
        val updatedList = currentKeywordList.filterNot { it == keyword }

        Log.d(TAG,"${keyword}를 제거한 로컬 검색 리스트: $updatedList")

        // 새로운 리스트 String으로 변환 후 저장
        dataStore.edit { prefs ->
            prefs[RECENT_KEYWORDS_KEY] = updatedList.joinToString(",")
        }
    }


    override suspend fun removeAll() {
        dataStore.edit { prefs -> prefs.remove(RECENT_KEYWORDS_KEY) }
    }


    companion object {
        private const val TAG = "SearchRepositoryImpl"
        private val RECENT_KEYWORDS_KEY = stringPreferencesKey("RecentKeywordsList")
    }
}