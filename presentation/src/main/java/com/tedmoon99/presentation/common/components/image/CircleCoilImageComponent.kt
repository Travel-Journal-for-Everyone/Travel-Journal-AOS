package com.tedmoon99.presentation.common.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun CircleCoilImageComponent(
    profileImageUrl: String,
    contentScale: ContentScale,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = profileImageUrl,
        contentScale = contentScale,
        contentDescription = null,
        modifier = modifier
    )
}