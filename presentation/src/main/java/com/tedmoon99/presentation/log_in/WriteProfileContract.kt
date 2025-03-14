package com.tedmoon99.presentation.log_in

import com.tedmoon99.presentation.common.viewmodel.UiEffect
import com.tedmoon99.presentation.common.viewmodel.UiEvent
import com.tedmoon99.presentation.common.viewmodel.UiState
import com.tedmoon99.presentation.log_in.utils.Scope

object WriteProfileContract {

    sealed class Event : UiEvent {
        data object ProfileImageClicked: Event()
        data object DoubleCheckClicked: Event()
        data object NameEntered: Event()
        data class ScopeClicked(val scope: Scope): Event()
    }

    data class State(
        val scope: Scope,
        val name: String,
        val isValidateName: Boolean = false,
        val isNotDuplicatedName: Boolean = false,
        val isLoading: Boolean = false,
        val isScopeOpened: Boolean = false,
    ): UiState

    sealed class Effect : UiEffect {
        data object NavigateToHome: Effect()
        data object ShowSuccessMessage: Effect()
        data object ShowCompleteFailedMessage: Effect()
        data object ShowDuplicatedErrorMessage: Effect()
        data object ShowBadWordErrorMessage: Effect()
    }

}