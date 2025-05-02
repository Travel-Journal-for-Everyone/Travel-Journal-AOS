package com.tedmoon99.presentation.search.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.tedmoon99.domain.search.model.SearchedDiary
import com.tedmoon99.domain.search.model.SearchedPlace
import com.tedmoon99.domain.search.model.SearchedUser
import com.tedmoon99.presentation.R
import com.tedmoon99.presentation.common.components.button.SearchBarButtonComponent
import com.tedmoon99.presentation.common.components.tab_layout.TabLayoutComponent
import com.tedmoon99.presentation.search.utils.SearchContract
import com.tedmoon99.presentation.search.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchScreen(
    hostState: SnackbarHostState,
    keywordFlow: StateFlow<String>,
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToSearchInput: (String) -> Unit, // 검색 화면으로 이동
    navigateToDiaryItemDetail: (Int) -> Unit, // 선택한 Item 조회 화면으로 이동
    navigateToPlaceItemDetail: (Int) -> Unit, // 선택한 Item 조회 화면으로 이동
    navigateToUserItemDetail: (Int) -> Unit, // 선택한 Item 조회 화면으로 이동
) {

    val keywordState by keywordFlow.collectAsStateWithLifecycle() // 검색 후 결과

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingData.collectAsLazyPagingItems()
    val effectFlow = viewModel.effect

    LaunchedEffect(keywordState) {
        viewModel.setEvent(SearchContract.Event.KeywordChanged(keywordState)) // 새로운 검색어로 검색 트리거
    }

    LaunchedEffect(Unit) {
        // effect 처리
        effectFlow.collect { effect ->
            when (effect) {
                is SearchContract.Effect.SearchBarClicked -> {
                    navigateToSearchInput(effect.keyword)
                }

                is SearchContract.Effect.SearchUserItemClicked -> {
                    navigateToUserItemDetail(effect.memberId)
                }

                is SearchContract.Effect.SearchDiaryItemClicked -> {
                    navigateToDiaryItemDetail(effect.diaryId)
                }

                is SearchContract.Effect.SearchPlaceItemClicked -> {
                    navigateToPlaceItemDetail(effect.placeId)
                }

                is SearchContract.Effect.ShowErrorMessage -> {
                    hostState.showSnackbar(
                        message = "시스템 오류. 관리자에게 문의하세요.",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Search
            SearchBarButtonComponent(
                placeholder = state.searchKeyword,
                icon = R.drawable.icon_search,
                onClicked = {
                    viewModel.triggerSearchBarClicked(state.searchKeyword)
                },
            )

            // Tab Layout
            TabLayoutComponent(
                selectedTab = state.selectedTab,
                tabs = listOf(
                    stringResource(R.string.label_tab_diary),
                    stringResource(R.string.label_tab_place),
                    stringResource(R.string.label_tab_user)
                ),
                onTabClicked = { viewModel.setEvent(SearchContract.Event.TabClicked(it)) },
                modifier = Modifier.fillMaxWidth()
            )

            // Item List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
            ) {
                items(pagingItems.itemCount) { index ->
                    when (val item = pagingItems[index]) {
                        is SearchedDiary -> {
                            // Diary Component
                        }

                        is SearchedPlace -> {
                            // Place Component
                        }

                        is SearchedUser -> {
                            // User Component
                            SearchUserItem(user = item)
                        }
                    }
                }
            }
        }
    }
}