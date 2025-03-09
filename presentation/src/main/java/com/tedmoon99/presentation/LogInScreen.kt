package com.tedmoon99.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tedmoon99.presentation.common.components.button.ButtonWithIconComponent
import com.tedmoon99.presentation.common.theme.Black
import com.tedmoon99.presentation.common.theme.Kakao_Yellow

@Composable
fun LogInScreen(
    hostState: SnackbarHostState,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(

            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // 공지글
            item {
                Spacer(modifier = Modifier.height(140.dp))

                Text(
                    text = "모두의 여행 일지와 함께\n" +
                            "나만의 여행 일기를\n" +
                            "만들어 보세요!",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // 로그인 / 회원가입
            item {
                Spacer(modifier = Modifier.height(360.dp))

                Text(
                    text = "로그인/회원가입",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth()
                    )
            }

            // 카카오
            item {
                Spacer(modifier = Modifier.height(10.dp))

                ButtonWithIconComponent(
                    icon = R.drawable.image_kakao,
                    label = R.string.kakao_login,
                    containerColor = Kakao_Yellow,
                    contentColor = Black,
                    iconColor = Black,
                    buttonShape = RoundedCornerShape(8.dp),
                    enabled = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    }
                )
            }

        }
    }
}