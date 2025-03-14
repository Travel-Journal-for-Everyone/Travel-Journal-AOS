package com.tedmoon99.presentation.log_in.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tedmoon99.presentation.R
import com.tedmoon99.presentation.common.components.button.ButtonComponent

@Composable
fun WelcomeScreen(
    name: String,
    navigateToHome: () -> Unit,
) {

    Scaffold(
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
                    onClick = navigateToHome,
                )
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
                .imePadding()
        ) {
            Spacer(modifier = Modifier.height(84.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${name}님 ",
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "가입완료!",
                    fontWeight = FontWeight.Thin,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "모두의 여행 일지",
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "와",
                    fontWeight = FontWeight.Thin,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Text(
                text = "추억을 만들어 보아요!",
                fontWeight = FontWeight.Thin,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}