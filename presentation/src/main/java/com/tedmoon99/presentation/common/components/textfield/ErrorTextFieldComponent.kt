package com.tedmoon99.presentation.common.components.textfield

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tedmoon99.presentation.common.theme.Gray06

@Composable
fun ErrorTextFieldComponent(
    inputText: String,
    placeHolder: String,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    cornerShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation,
    singleLine: Boolean,
    isError: Boolean,
    onValueChange: (String) -> Unit,
) {

    TextField(
        value = inputText,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Gray06,
            focusedContainerColor = Gray06,
            errorContainerColor = Gray06,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = placeHolder,
                style = MaterialTheme.typography.displayMedium
            )
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorMessage?: "",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        },
        shape = cornerShape,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        modifier = modifier,
        onValueChange = onValueChange,
    )
}