package com.tedmoon99.presentation.follow.utils

import com.tedmoon99.presentation.common.viewmodel.UiEffect
import com.tedmoon99.presentation.common.viewmodel.UiEvent
import com.tedmoon99.presentation.common.viewmodel.UiState

object FollowContract {

    sealed class Event : UiEvent {
        data class TabClicked(val tab: Int): Event()
        data class FollowerDeleteClicked(val memberId: Int): Event()
        data class FollowingDeleteClicked(val memberId: Int): Event()
        data class FollowerRequestApprove(val followId: Int): Event()
        data class FollowerRequestDeny(val followId: Int): Event()
    }

    data class State(
        val selectedTab: Int,
        val isLoading: Boolean,
    ): UiState

    sealed class Effect : UiEffect {
        data object NavigateToBack: Effect()
        data class NavigateToOtherProfile(val memberId: Int): Effect()
    }
}