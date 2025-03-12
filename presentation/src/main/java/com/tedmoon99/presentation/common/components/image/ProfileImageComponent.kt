package com.tedmoon99.presentation.common.components.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.tedmoon99.presentation.R
import com.tedmoon99.presentation.common.theme.Gray06
import com.tedmoon99.presentation.common.theme.White

@Composable
fun ProfileImageComponent(
    imageSize: Int,
    modifier: Modifier = Modifier,
    @DrawableRes profileImage: Int = R.drawable.image_basic_profile,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        // 배경 이미지
        CircleImageComponent(
            image = profileImage,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageSize.dp)
                .align(Alignment.Center)
        )

        // 추가 요소 이미지
        CircleImageComponent(
            image = R.drawable.icon_camera,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(White)
                .border(1.dp, Gray06, CircleShape),
        )

    }

}