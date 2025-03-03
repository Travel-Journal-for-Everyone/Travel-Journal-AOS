package com.tedmoon99.presentation.common.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Dark_Primary,
    secondary = Dark_Primary_sub,
    background = Dark_White,
    surface = Dark_Gray07,
    onPrimary = Dark_White,
    onSecondary = Dark_Gray07,
    onBackground = Dark_Black,
    onSurface = Dark_Black,
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Primary_sub,
    background = White,
    surface = Gray07,
    onPrimary = White,
    onSecondary = Gray07,
    onBackground = Black,
    onSurface = Black,
)

@Composable
fun TravelJournalForEveryoneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}