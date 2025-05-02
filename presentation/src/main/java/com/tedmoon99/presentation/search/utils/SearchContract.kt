package com.tedmoon99.presentation.search.utils

import androidx.paging.PagingData
import com.tedmoon99.domain.search.model.SearchResultItem
import com.tedmoon99.presentation.common.viewmodel.UiEffect
import com.tedmoon99.presentation.common.viewmodel.UiEvent
import com.tedmoon99.presentation.common.viewmodel.UiState

object SearchContract {

    sealed class Event: UiEvent{
        data class TabClicked(val tab: Int): Event()
        data class KeywordChanged(val keyword: String): Event()
    }

    data class State(
        val selectedTab: Int = 0,
        val searchKeyword: String,
        val pagingData: PagingData<SearchResultItem> = PagingData.empty(),
        val isLoading: Boolean = false,
        val error: String? = null
    ): UiState

    sealed class Effect: UiEffect {
        data class SearchBarClicked(val keyword: String): Effect()
        data class SearchUserItemClicked(val memberId: Int): Effect()
        data class SearchDiaryItemClicked(val diaryId: Int): Effect()
        data class SearchPlaceItemClicked(val placeId: Int): Effect()
        data object ShowErrorMessage: Effect()
    }
}