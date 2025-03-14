package com.tedmoon99.presentation.log_in.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tedmoon99.presentation.R
import com.tedmoon99.presentation.common.components.appbar.BasicTopAppbarComponent
import com.tedmoon99.presentation.common.components.button.ButtonComponent
import com.tedmoon99.presentation.common.components.image.ProfileImageComponent
import com.tedmoon99.presentation.common.components.text.TrailingIconContentTextComponent
import com.tedmoon99.presentation.common.components.text.TrailingIconTextComponent
import com.tedmoon99.presentation.common.components.textfield.ErrorTextFieldComponent
import com.tedmoon99.presentation.common.theme.Gray06
import com.tedmoon99.presentation.common.theme.White
import com.tedmoon99.presentation.log_in.WriteProfileContract
import com.tedmoon99.presentation.log_in.utils.Scope
import com.tedmoon99.presentation.log_in.viewmodel.WriteProfileViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WriteProfileScreen(
    hostState: SnackbarHostState,
    viewModel: WriteProfileViewModel = hiltViewModel(),
    navigateToWelcome: () -> Unit,
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val coroutine = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scopeList = listOf(Scope.PUBLIC_SCOPE, Scope.ONLY_FOLLOW_SCOPE, Scope.PRIVATE_SCOPE)

    LaunchedEffect(state.isScopeOpened) {
        effectFlow.collect { effect ->
            when (effect) {
                is WriteProfileContract.Effect.NavigateToHome -> {
                    // 웰컴 페이지로 이동
                    navigateToWelcome()
                }

                is WriteProfileContract.Effect.ShowCompleteFailedMessage -> {
                    hostState.showSnackbar(
                        message = "회원가입 실패. 관리자에게 문의하세요.",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }

                is WriteProfileContract.Effect.ShowDuplicatedErrorMessage -> {
                    hostState.showSnackbar(
                        message = "이미 사용중인 아이디입니다.",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }

                is WriteProfileContract.Effect.ShowBadWordErrorMessage -> {
                    hostState.showSnackbar(
                        message = "부적절한 단어가 포함되어 있습니다",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
                is WriteProfileContract.Effect.ShowSuccessMessage -> {
                    hostState.showSnackbar(
                        message = "사용가능한 닉네임입니다.",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            BasicTopAppbarComponent(
                title = "프로필 작성",
                leadingIcon = R.drawable.ic_launcher_foreground, // icon 안 보임
                trailingIcon = R.drawable.ic_launcher_foreground, // icon 안 보임
                showLeadingIcon = false,
                showTrailingIcon = false,
                modifier = Modifier.fillMaxWidth()
            )
        },

        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp)
            ) {
                ButtonComponent(
                    label = stringResource(R.string.label_write_complete),
                    buttonShape = RoundedCornerShape(8.dp),
                    enabled = state.isNotDuplicatedName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    onClick = {
                        viewModel.triggerSignUpComplete()
                        keyboardController?.hide() // 키보드 닫기
                    },
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = hostState) }
    ) { innerPadding ->

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(35.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .clickable { focusManager.clearFocus() }
                .padding(innerPadding)
                .imePadding()
        ) {

            item {
                Spacer(modifier = Modifier.height(40.dp))

                // Profile Image
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProfileImageComponent(
                        imageSize = 120,
                        modifier = Modifier.size(120.dp),
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            // 닉네임
            item {

                Text(
                    text = "닉네임",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                ) {
                    ErrorTextFieldComponent(
                        inputText = state.name,
                        placeHolder = stringResource(R.string.placeholder_input, "닉네임"),
                        isError = state.name.isNotEmpty() && !state.isValidateName,
                        errorMessage = when {
                            !state.isValidateName -> stringResource(R.string.error_message_name_input) // 형식 오류
                            else -> null
                        },
                        cornerShape = RoundedCornerShape(8.dp),

                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        ),
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .bringIntoViewRequester(bringIntoViewRequester), // 자동 스크롤
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        onValueChange = {
                            viewModel.triggerNameCheck(it)
                            coroutine.launch {
                                bringIntoViewRequester.bringIntoView() // 입력 시 자동 스크롤
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // 중복 확인
                    ButtonComponent(
                        label = stringResource(R.string.label_double_check),
                        buttonShape = RoundedCornerShape(8.dp),
                        enabled = state.name.isNotEmpty() && state.isValidateName,
                        modifier = Modifier
                            .height(56.dp)
                            .weight(1f),
                        onClick = {
                            viewModel.triggerNameDoubleCheck()
                            keyboardController?.hide()
                        }
                    )
                }

            }

            // 프로필 공개 범위
            item {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "프로필 공개 범위",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 프로필 공개 범위
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Gray06, shape = RoundedCornerShape(8.dp))
                        .clickable(onClick = {
                            // 현재 상태 전송
                            viewModel.setEvent(WriteProfileContract.Event.ScopeClicked(state.scope))
                        })
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    TrailingIconTextComponent(
                        text = state.scope.title,
                        icon = state.scope.icon
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = if (!state.isScopeOpened) painterResource(R.drawable.icon_arrow_bottom) else painterResource(
                            R.drawable.icon_arrow_up
                        ),
                        contentDescription = null,
                    )
                }

                // Drop Box
                AnimatedVisibility(
                    visible = state.isScopeOpened,
                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(White, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Gray06, RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessVeryLow
                                )
                            ),
                    ) {
                        scopeList.forEach { scope: Scope ->
                            TrailingIconContentTextComponent(
                                text = scope.title,
                                content = scope.message,
                                icon = scope.icon,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = {
                                        // 클릭한 상태 전송
                                        viewModel.setEvent(WriteProfileContract.Event.ScopeClicked(scope))
                                    })
                                    .padding(vertical = 16.dp, horizontal = 20.dp)
                            )
                            HorizontalDivider(thickness = 1.dp, color = Gray06)
                        }
                    }
                }
            }
        }
    }
}