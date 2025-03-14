package com.tedmoon99.presentation.log_in

import com.tedmoon99.presentation.common.viewmodel.UiEffect
import com.tedmoon99.presentation.common.viewmodel.UiEvent
import com.tedmoon99.presentation.common.viewmodel.UiState

object LogInContract {

    sealed class Event : UiEvent {
        data object KakaoLogInClicked : Event()
        data object AppleLogInClicked : Event()
        data object GoogleLogInClicked : Event()
    }

    data class State(
        val isLoggedIn: Boolean = false,
        val isLoading: Boolean = false
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToWriteProfile: Effect()
        data object NavigateToHome: Effect()
        data object ShowErrorMessage : Effect()
    }
}