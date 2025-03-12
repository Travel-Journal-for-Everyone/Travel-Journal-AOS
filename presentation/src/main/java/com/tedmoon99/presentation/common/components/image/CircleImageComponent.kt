package com.tedmoon99.presentation.common.components.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun CircleImageComponent(
    @DrawableRes image: Int,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(image),
        contentScale = contentScale,
        contentDescription = null,
        modifier = modifier.clip(CircleShape), // 원형으로 자름
        alignment = Alignment.Center,
    )
}