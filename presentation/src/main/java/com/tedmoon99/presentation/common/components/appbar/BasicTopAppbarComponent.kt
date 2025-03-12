package com.tedmoon99.presentation.common.components.appbar

import androidx.annotation.DrawableRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.tedmoon99.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopAppbarComponent(
    title: String,
    @DrawableRes leadingIcon: Int,
    @DrawableRes trailingIcon: Int,
    modifier: Modifier = Modifier,
    showLeadingIcon: Boolean = false,
    showTrailingIcon: Boolean = false,
    onClickLeadingIcon: (() -> Unit)? = null,
    onClickTrailingIcon: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = modifier,
            )
        },
        navigationIcon = {
            if (showLeadingIcon && onClickLeadingIcon != null) {
                IconButton(onClick = onClickLeadingIcon) {
                    Icon(
                        painter = painterResource(leadingIcon),
                        contentDescription = stringResource(R.string.topbar_image_leadingIcon)
                    )
                }
            } else {
                // 빈 공간 차지
                IconButton(
                    enabled = false,
                    onClick = {}
                ) { }
            }
        },
        actions = {
            if (showTrailingIcon && onClickTrailingIcon != null) {
                IconButton(onClick = onClickTrailingIcon) {
                    Icon(
                        painter = painterResource(trailingIcon),
                        contentDescription = stringResource(R.string.topbar_image_trailingIcon)
                    )
                }
            } else {
                // 빈 공간 차지
                IconButton(
                    enabled = false,
                    onClick = {}
                ) { }
            }
        }
    )

}