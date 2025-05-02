package com.tedmoon99.presentation.search.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tedmoon99.presentation.R
import com.tedmoon99.presentation.common.components.textfield.SearchBarTextFieldComponent
import com.tedmoon99.presentation.common.theme.Black
import com.tedmoon99.presentation.common.theme.Gray02
import com.tedmoon99.presentation.search.utils.SearchInputContract
import com.tedmoon99.presentation.search.viewmodel.SearchInputViewModel
import kotlinx.coroutines.launch


@Composable
fun SearchInputScreen(
    hostState: SnackbarHostState,
    viewModel: SearchInputViewModel = hiltViewModel(),
    navigateToSearch: (String) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val coroutine = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        // effect 처리
        effectFlow.collect { effect ->
            when (effect) {
                is SearchInputContract.Effect.NavigateToSearch -> {
                    // 검색 완료 후 이동
                    navigateToSearch(state.keyword)
                }
            }
        }
    }


    Scaffold { innerPadding ->

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {

            // 검색바
            SearchBarTextFieldComponent(
                text = state.keyword,
                placeholder = stringResource(R.string.placeholder_search),
                icon = R.drawable.icon_search,
                visualTransformation = VisualTransformation.None,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus() // 키보드 내리기
                        viewModel.triggerKeywordItemClick(viewModel.currentState.keyword)
                    }
                ),
                onValueChanged = { viewModel.triggerKeywordChanged(it) }
            )

            // 최근 검색어, 전체 삭제
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 최근 검색어
                Text(
                    text = stringResource(R.string.text_recent_search),
                    style = MaterialTheme.typography.titleMedium,
                    color = Black
                )

                Spacer(modifier = Modifier.weight(1f)) // 빈 공간 차지

                // 전체 삭제
                TextButton(
                    onClick = {
                        coroutine.launch {
                            hostState.showSnackbar(
                                message = "전체 삭제 완료",
                                duration = SnackbarDuration.Short,
                            )
                        }
                        viewModel.triggerRemoveAll()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.text_remove_all),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray02,
                    )
                }
            }

            // Item 리스트
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                itemsIndexed(
                    items = state.recentSearchKeyword,
                    key = { _, item -> item }
                ) { _: Int, keyword: String ->
                    SearchKeywordItem(
                        keyword = keyword,
                        onItemClicked = { viewModel.triggerKeywordItemClick(keyword) },
                        onDeleteClicked = {
                            coroutine.launch {
                                hostState.showSnackbar(
                                    message = "$keyword 삭제 완료",
                                    duration = SnackbarDuration.Short,
                                )
                            }
                            viewModel.triggerDeleteItem(keyword)
                        },
                    )
                }
            }
        }
    }
}