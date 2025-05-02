package com.tedmoon99.presentation.search.utils

import com.tedmoon99.presentation.common.viewmodel.UiEffect
import com.tedmoon99.presentation.common.viewmodel.UiEvent
import com.tedmoon99.presentation.common.viewmodel.UiState

object SearchInputContract {

    sealed class Event : UiEvent {
        data object OnRemoveAllClicked: Event()
        data class OnDeleteItemClicked(val keyword: String): Event()
        data class OnKeywordItemClicked(val keyword: String): Event()
        data class OnSearchedKeywordChanged(val keyword: String): Event()
    }

    data class State(
        val keyword: String = "",
        val recentSearchKeyword: List<String> = emptyList(),
        val isLoading: Boolean = false,
    ) : UiState


    sealed class Effect : UiEffect {
        data class NavigateToSearch(val keyword: String): Effect()
    }
}