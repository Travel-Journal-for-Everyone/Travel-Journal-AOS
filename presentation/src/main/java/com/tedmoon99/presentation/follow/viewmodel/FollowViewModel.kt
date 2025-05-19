package com.tedmoon99.presentation.follow.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tedmoon99.domain.follow.model.FollowRequest
import com.tedmoon99.domain.follow.model.Follower
import com.tedmoon99.domain.follow.model.Following
import com.tedmoon99.domain.follow.usecase.FollowUseCase
import com.tedmoon99.presentation.common.viewmodel.BaseViewModel
import com.tedmoon99.presentation.follow.utils.FollowContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class FollowViewModel @Inject constructor(
    private val followUseCase: FollowUseCase,
) : BaseViewModel<FollowContract.Event, FollowContract.State, FollowContract.Effect>() {

    private val refreshRequestTrigger = MutableStateFlow(Unit)
    private val refreshFollowingTrigger = MutableStateFlow(Unit)
    private val refreshFollowerTrigger = MutableStateFlow(Unit)

    val followingListItemPagingData: Flow<PagingData<Following>> = refreshFollowingTrigger.flatMapLatest { followUseCase.getFollowingList() }.cachedIn(viewModelScope)
    val followerListItemPagingData: Flow<PagingData<Follower>> = refreshFollowerTrigger.flatMapLatest { followUseCase.getFollowerList() }.cachedIn(viewModelScope)
    val followRequestItemPagingData: Flow<PagingData<FollowRequest>> = refreshRequestTrigger.flatMapLatest { followUseCase.getFollowRequestList() }.cachedIn(viewModelScope)

    init {
        refreshFollowingList()
        refreshFollowerList()
    }

    override fun createInitialState(): FollowContract.State {
        return FollowContract.State(
            selectedTab = 0,
            isLoading = false
        )
    }

    override fun handleEvent(event: FollowContract.Event) {
        when (event) {
            is FollowContract.Event.TabClicked -> {
                handleTabClick(event.tab)
            }

            is FollowContract.Event.FollowingDeleteClicked -> {
                handleFollowingDelete(event.memberId)
            }

            is FollowContract.Event.FollowerDeleteClicked -> {
                handleFollowerDelete(event.memberId)
            }

            is FollowContract.Event.FollowerRequestApprove -> {
                handleFollowerRequestApprove(event.followId)
            }

            is FollowContract.Event.FollowerRequestDeny -> {
                handleFollowerRequestDeny(event.followId)
            }
        }
    }

    private fun refreshFollowingList() {
        viewModelScope.launch {
            setState(currentState.copy(isLoading = true))
            refreshFollowingTrigger.emit(Unit)
            setState(currentState.copy(isLoading = false))
        }
    }

    private fun refreshFollowerList() {
        viewModelScope.launch {
            setState(currentState.copy(isLoading = true))
            refreshFollowerTrigger.emit(Unit)
            setState(currentState.copy(isLoading = false))
        }
    }

    private fun handleFollowerRequestApprove(followId: Int) {
        viewModelScope.launch {
            setState(currentState.copy(isLoading = true))
            followUseCase.approveFollowRequest(followId)
            setState(currentState.copy(isLoading = false))
        }
    }

    private fun handleFollowerRequestDeny(followId: Int) {
        viewModelScope.launch {
            setState(currentState.copy(isLoading = true))
            followUseCase.denyFollowRequest(followId)
            setState(currentState.copy(isLoading = false))
        }
    }

    private fun handleTabClick(tabId: Int) {
        when (tabId) {
            0 -> {
                // Follower
                refreshFollowerList()
            }

            1 -> {
                // Following
                refreshFollowingList()
            }
        }
        setState(currentState.copy(selectedTab = tabId))
    }


    private fun handleFollowingDelete(memberId: Int) {
        viewModelScope.launch {
            setState(currentState.copy(isLoading = true))
            followUseCase.deleteFollowingItem(memberId)
            setState(currentState.copy(isLoading = false))
        }
    }

    private fun handleFollowerDelete(memberId: Int) {
        viewModelScope.launch {
            setState(currentState.copy(isLoading = true))
            followUseCase.deleteFollowerItem(memberId)
            setState(currentState.copy(isLoading = false))
        }
    }

    fun triggerFollowerDelete(memberId: Int) = setEvent(FollowContract.Event.FollowerDeleteClicked(memberId))

    fun triggerFollowingDelete(memberId: Int) = setEvent(FollowContract.Event.FollowingDeleteClicked(memberId))

    fun triggerRequestApprove(followId: Int) = setEvent(FollowContract.Event.FollowerRequestApprove(followId))

    fun triggerRequestDeny(followId: Int) = setEvent(FollowContract.Event.FollowerRequestDeny(followId))

    fun triggerTabClickEvent(tabId: Int) = setEvent(FollowContract.Event.TabClicked(tabId))

    fun triggerNavigateToBack() = setEffect(FollowContract.Effect.NavigateToBack)

    fun triggerNavigateToOtherProfile(memberId: Int) = setEffect(FollowContract.Effect.NavigateToOtherProfile(memberId))

    companion object {
        private const val TAG = "FollowViewModel"
    }

}