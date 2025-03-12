package com.tedmoon99.presentation.common.components.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ButtonComponent(
    label: String,
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
            text = label,
            textAlign = TextAlign.Center,
        )
    }
}