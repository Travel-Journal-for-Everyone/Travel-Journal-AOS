package com.tedmoon99.presentation.common.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tedmoon99.presentation.common.theme.Black
import com.tedmoon99.presentation.common.theme.Gray04

@Composable
fun OutlinedButtonComponent(
    label: String,
    buttonShape: RoundedCornerShape,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    OutlinedButton(
        shape = buttonShape,
        enabled = enabled,
        modifier = modifier,
        border = BorderStroke(1.dp, Gray04),
        onClick = onClick,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.displaySmall,
            color = Black
        )
    }
}