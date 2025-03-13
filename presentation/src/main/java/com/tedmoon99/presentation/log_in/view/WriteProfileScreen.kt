package com.tedmoon99.presentation.log_in.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tedmoon99.presentation.R
import com.tedmoon99.presentation.common.components.appbar.BasicTopAppbarComponent
import com.tedmoon99.presentation.common.components.button.ButtonComponent
import com.tedmoon99.presentation.common.components.image.ProfileImageComponent
import com.tedmoon99.presentation.common.components.text.TrailingIconTextComponent
import com.tedmoon99.presentation.common.components.textfield.ErrorTextFieldComponent
import com.tedmoon99.presentation.common.theme.Gray06
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WriteProfileScreen(

) {

    val coroutine = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

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
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    onClick = {
                        keyboardController?.hide() // 키보드 닫기

                    },
                )
            }
        }
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
                        inputText = "",
                        placeHolder = stringResource(R.string.placeholder_input, "닉네임", 2, 12),
                        isError = "nameState".isNotEmpty() && !true,
                        errorMessage = R.string.error_message_name,
                        cornerShape = RoundedCornerShape(8.dp),

                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        ),
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .bringIntoViewRequester(bringIntoViewRequester), // 자동 스크롤
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        onValueChange = {
//                        viewModel.setName(it)
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
                        enabled = true,
                        modifier = Modifier
                            .height(56.dp)
                            .weight(1f),
                        onClick = {
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Gray06, shape = RoundedCornerShape(8.dp))
                        .clickable(true, onClick = {})
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    TrailingIconTextComponent(
                        text = "전체 공개",
                        icon = R.drawable.icon_world
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = painterResource(R.drawable.icon_arrow_bottom),
                        contentDescription = null,
                    )
                }
            }
        }

    }

}