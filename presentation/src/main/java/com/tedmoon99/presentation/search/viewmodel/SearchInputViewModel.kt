package com.tedmoon99.presentation.search.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tedmoon99.domain.search.usecase.SearchKeywordUseCase
import com.tedmoon99.presentation.common.viewmodel.BaseViewModel
import com.tedmoon99.presentation.search.utils.SearchInputContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchInputViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchKeywordUseCase: SearchKeywordUseCase,
) : BaseViewModel<SearchInputContract.Event, SearchInputContract.State, SearchInputContract.Effect>() {

    override fun createInitialState(): SearchInputContract.State {
        return SearchInputContract.State()
    }

    override fun handleEvent(event: SearchInputContract.Event) {
        when (event) {
            is SearchInputContract.Event.OnRemoveAllClicked -> {
                treatRemoveAll()
            }

            is SearchInputContract.Event.OnSearchedKeywordChanged -> {
                treatKeywordChanged(event.keyword)
            }

            is SearchInputContract.Event.OnDeleteItemClicked -> {
                treatDeleteItem(event.keyword)
                getAllKeyword()
            }

            is SearchInputContract.Event.OnKeywordItemClicked -> {
                treatKeywordItemClick(event.keyword)
            }
        }
    }

    init {
        initData()
        getAllKeyword() // 최근 검색어 조회
    }

    private fun initData() {
        val initialKeyword = savedStateHandle.get<String>("keyword") ?: ""
        setState(currentState.copy(keyword = initialKeyword))
    }

    private fun getAllKeyword() = viewModelScope.launch {
        val result = searchKeywordUseCase.getAllKeyword()
        setState(currentState.copy(recentSearchKeyword = result))
    }

    private fun treatKeywordChanged(keyword: String) {
        setState(currentState.copy(keyword = keyword))
    }

    private fun treatRemoveAll() = viewModelScope.launch {
        searchKeywordUseCase.removeAll()
        val result = searchKeywordUseCase.getAllKeyword()
        setState(currentState.copy(recentSearchKeyword = result))
    }

    private fun treatDeleteItem(keyword: String) = viewModelScope.launch {
        searchKeywordUseCase.removeRecentSearchKeyword(keyword)
        setState(currentState.copy(recentSearchKeyword = currentState.recentSearchKeyword - keyword))
    }

    private fun treatKeywordItemClick(keyword: String) {
        setState(currentState.copy(keyword))
        viewModelScope.launch {
            launch(Dispatchers.IO) { searchKeywordUseCase.setRecentSearchKeyword(keyword) } // 비동기로 저장 처리
            setEffect(SearchInputContract.Effect.NavigateToSearch(keyword)) // 즉시 화면 전환
        }
    }

    fun triggerKeywordChanged(keyword: String) {
        setEvent(SearchInputContract.Event.OnSearchedKeywordChanged(keyword))
    }

    fun triggerRemoveAll() {
        setEvent(SearchInputContract.Event.OnRemoveAllClicked)
    }

    fun triggerDeleteItem(keyword: String) {
        setEvent(SearchInputContract.Event.OnDeleteItemClicked(keyword))
    }

    fun triggerKeywordItemClick(keyword: String) {
        setEvent(SearchInputContract.Event.OnKeywordItemClicked(keyword))
    }
}