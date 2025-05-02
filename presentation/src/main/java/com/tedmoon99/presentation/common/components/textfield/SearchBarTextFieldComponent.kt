package com.tedmoon99.presentation.common.components.textfield

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tedmoon99.presentation.common.theme.Gray03
import com.tedmoon99.presentation.common.theme.Gray06

@Composable
fun SearchBarTextFieldComponent(
    text: String,
    placeholder: String,
    @DrawableRes icon: Int,
    visualTransformation: VisualTransformation,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
) {
    TextField(
        value = text,
        textStyle = MaterialTheme.typography.displayMedium,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Gray06,
            focusedContainerColor = Gray06,
            unfocusedTextColor = Gray03,
            focusedTextColor = Gray03,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        trailingIcon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Gray03,
                modifier = Modifier.size(24.dp)
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                color = Gray03,
            )
        },
        onValueChange = onValueChanged,
        singleLine = true,
        isError = false,
        modifier = modifier
            .fillMaxWidth(),
    )
}