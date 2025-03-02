package com.tedmoon99.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tedmoon99.presentation.R


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

)

val Pretendard = FontFamily(
    Font(R.font.pretendard_bold),
    Font(R.font.pretendard_semibold),
    Font(R.font.pretendard_regular),
    Font(R.font.pretendard_medium),
)