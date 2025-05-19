package com.tedmoon99.presentation.follow.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.tedmoon99.presentation.R
import com.tedmoon99.presentation.common.components.appbar.BasicTopAppbarComponent
import com.tedmoon99.presentation.common.components.tab_layout.TabLayoutComponent
import com.tedmoon99.presentation.common.theme.Gray05
import com.tedmoon99.presentation.common.theme.White
import com.tedmoon99.presentation.follow.utils.FollowContract
import com.tedmoon99.presentation.follow.viewmodel.FollowViewModel

@Composable
fun FollowScreen(
    viewModel: FollowViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    navigateToOtherProfile: (Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val followingListItems = viewModel.followingListItemPagingData.collectAsLazyPagingItems()
    val followerListItems = viewModel.followerListItemPagingData.collectAsLazyPagingItems()
    val followRequestListItems = viewModel.followRequestItemPagingData.collectAsLazyPagingItems()
    val effect = viewModel.effect

    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when (effect) {
                is FollowContract.Effect.NavigateToBack -> {
                    navigateToBack()
                }
                is FollowContract.Effect.NavigateToOtherProfile -> {
                    navigateToOtherProfile(effect.memberId)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            BasicTopAppbarComponent(
                title = stringResource(R.string.title_follow, "여행자"),
                leadingIcon = R.drawable.icon_arrow_back,
                trailingIcon = R.drawable.ic_launcher_foreground, // icon 안 보임
                showLeadingIcon = true,
                showTrailingIcon = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White),
                onClickLeadingIcon = { viewModel.triggerNavigateToBack() }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // tab
            TabLayoutComponent(
                selectedTab = state.selectedTab,
                tabs = listOf(
                    stringResource(R.string.label_tab_follower, followerListItems.itemCount),
                    stringResource(R.string.label_tab_following, followingListItems.itemCount)
                ),
                onTabClicked = { viewModel.triggerTabClickEvent(it) },
                modifier = Modifier.fillMaxWidth()
            )

            // Item
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Follow 요청
                if (state.selectedTab == 0) {
                    // Follow 요청 존재
                    if (followRequestListItems.itemCount != 0) {
                        item {
                            Text(
                                text = stringResource(R.string.text_request, followRequestListItems.itemCount),
                                style = MaterialTheme.typography.displayMedium,
                            )
                        }

                        items(followRequestListItems.itemCount) { index: Int ->
                            val followId = followerListItems[index]?.memberId ?: 0

                            FollowRequestListItem(
                                nickName = followRequestListItems[index]?.nickName ?: "",
                                profileImageUrl = followRequestListItems[index]?.profileImageUrl ?: "",
                                diaryCount = followRequestListItems[index]?.diaryCount ?: 0,
                                placeCount = followRequestListItems[index]?.placeCount ?: 0,
                                onApproveClicked = { viewModel.triggerRequestApprove(followId = followId) },
                                onDenyClicked = { viewModel.triggerRequestDeny(followId = followId) }
                            )

                        }

                        item {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 1.dp,
                                color = Gray05
                            )
                        }
                    }

                    // Follower List
                    items(followerListItems.itemCount) { index: Int ->
                        val memberId = followerListItems[index]?.memberId ?: 0

                        FollowListItem(
                            nickName = followerListItems[index]?.nickName ?: "",
                            profileImageUrl = followerListItems[index]?.profileImageUrl ?: "",
                            diaryCount = followerListItems[index]?.diaryCount ?: 0,
                            placeCount = followerListItems[index]?.placeCount ?: 0,
                            onItemClicked = { viewModel.triggerNavigateToOtherProfile(memberId) },
                            onDeleteClicked = { viewModel.triggerFollowerDelete(memberId) },
                        )
                    }
                }

                // Following List
                items(followingListItems.itemCount) { index: Int ->
                    val memberId = followingListItems[index]?.memberId ?: 0

                    FollowListItem(
                        nickName = followingListItems[index]?.nickName ?: "",
                        profileImageUrl = followingListItems[index]?.profileImageUrl ?: "",
                        diaryCount = followingListItems[index]?.diaryCount ?: 0,
                        placeCount = followingListItems[index]?.placeCount ?: 0,
                        onItemClicked = { viewModel.triggerNavigateToOtherProfile(memberId) },
                        onDeleteClicked = { viewModel.triggerFollowingDelete(memberId) },
                    )
                }
            }
        }
    }
}