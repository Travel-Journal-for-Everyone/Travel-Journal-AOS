package com.tedmoon99.presentation.common.components.text

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tedmoon99.presentation.R
import com.tedmoon99.presentation.common.theme.Gray02

@Composable
fun LeadingIconTextComponent(
    @DrawableRes icon: Int,
    text: String,
    iconColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    val fontSize = MaterialTheme.typography.displaySmall.fontSize
    val iconSize = with(LocalDensity.current) { fontSize.toDp() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(iconSize)
        )
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.displaySmall
        )
    }
}


@Preview
@Composable
private fun LeadingIconTextPreview() {

    val count = 2
    Surface {
        LeadingIconTextComponent(
            icon = R.drawable.icon_paper,
            text = count.toString(),
            iconColor = Gray02,
            textColor = Gray02,
            modifier = Modifier
        )

    }
}