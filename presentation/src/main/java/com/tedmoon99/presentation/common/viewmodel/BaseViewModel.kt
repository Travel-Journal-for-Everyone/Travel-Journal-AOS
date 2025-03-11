package com.tedmoon99.presentation.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface UiState
interface UiEffect
interface UiEvent

abstract class BaseViewModel<Event: UiEvent, State: UiState, Effect: UiEffect>: ViewModel() {
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    // State
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()
    val currentState: State get() = _uiState.value

    // Event
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow() // 구독자가 없는 경우, 즉시 이벤트를 삭제하기 위하여 SharedFlow() 사용
    val event = _event.asSharedFlow()

    // Effect
    private val _effect: Channel<Effect> = Channel() // 연속적인 단발성 이벤트를 순차적으로 전파
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)

    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch {
            _event.emit(newEvent)
        }
    }

    protected fun setState(state: State) {
        _uiState.value = state
    }

    protected fun setEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}