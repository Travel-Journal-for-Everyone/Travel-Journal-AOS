package com.tedmoon99.presentation.common.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ButtonComponent(
    @StringRes label: Int,
    buttonShape: RoundedCornerShape,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        shape = buttonShape,
        enabled = enabled,
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = stringResource(label),
            textAlign = TextAlign.Center,
            modifier = modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        )
    }
}