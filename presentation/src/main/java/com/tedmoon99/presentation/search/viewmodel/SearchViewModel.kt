package com.tedmoon99.presentation.search.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.tedmoon99.domain.search.model.SearchResultItem
import com.tedmoon99.domain.search.usecase.SearchUseCase
import com.tedmoon99.domain.search.utils.SearchTabType
import com.tedmoon99.presentation.common.viewmodel.BaseViewModel
import com.tedmoon99.presentation.search.utils.SearchContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
) : BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {

    // Paging Data
    private val _pagingData = MutableStateFlow<PagingData<SearchResultItem>>(PagingData.empty())
    val pagingData: StateFlow<PagingData<SearchResultItem>> = _pagingData.asStateFlow()

    override fun createInitialState(): SearchContract.State {
        return SearchContract.State(
            selectedTab = 0,
            searchKeyword = "",
            isLoading = false,
            error = null
        )
    }

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            // 선택된 탭 타입 변경
            is SearchContract.Event.TabClicked -> {
                setState(currentState.copy(selectedTab = event.tab))
                search()
            }
            // 검색 키워드 변경
            is SearchContract.Event.KeywordChanged -> {
                setState(currentState.copy(searchKeyword = event.keyword))
                search()
            }
        }
    }

    private fun search(){
        viewModelScope.launch {
            val selectedTab = currentState.selectedTab
            val searchKeyword = currentState.searchKeyword
            Log.d(TAG, "선택된 탭: $selectedTab\n검색 키워드: $searchKeyword")
            val tab = handleTabId(selectedTab)
            setState(currentState.copy(isLoading = true))
            try {
                val items: Flow<PagingData<SearchResultItem>> = searchUseCase.getItems(searchKeyword, tab)
                items.collectLatest { item -> _pagingData.value = item }
            } finally {
                setState(currentState.copy(isLoading = false))
            }
        }
    }

    private fun handleTabId(tabId: Int): SearchTabType {
        return when (tabId) {
            0 -> SearchTabType.Diary
            1 -> SearchTabType.Place
            2 -> SearchTabType.User
            else -> SearchTabType.WrongTypeSearch
        }
    }


    fun triggerSearchBarClicked(keyword: String) {
        setEffect(SearchContract.Effect.SearchBarClicked(keyword))
    }

    fun triggerUserItemClicked(memberId: Int) {
        setEffect(SearchContract.Effect.SearchUserItemClicked(memberId))
    }

    fun triggerDiaryItemClicked(diaryId: Int) {
        setEffect(SearchContract.Effect.SearchDiaryItemClicked(diaryId))
    }

    fun triggerPlaceItemClicked(placeId: Int) {
        setEffect(SearchContract.Effect.SearchPlaceItemClicked(placeId))
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}