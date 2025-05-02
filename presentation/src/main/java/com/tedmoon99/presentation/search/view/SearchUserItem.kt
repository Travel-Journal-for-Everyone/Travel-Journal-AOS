package com.tedmoon99.presentation.search.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tedmoon99.domain.search.model.SearchedUser
import com.tedmoon99.presentation.R
import com.tedmoon99.presentation.common.components.image.CircleCoilImageComponent
import com.tedmoon99.presentation.common.components.text.LeadingIconTextComponent
import com.tedmoon99.presentation.common.theme.Gray02
import com.tedmoon99.presentation.common.theme.TravelJournalForEveryoneTheme

@Composable
fun SearchUserItem(
    user: SearchedUser,
    modifier: Modifier = Modifier,
) {
    var columnHeight by remember { mutableStateOf(0) }
    val iconHeight = with(LocalDensity.current) { columnHeight.toDp() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        // Image
        CircleCoilImageComponent(
            profileImageUrl = user.profileImageUrl,
            contentScale = ContentScale.Crop,
            modifier = modifier.height(iconHeight).aspectRatio(1f)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                columnHeight = coordinates.size.height
            }
        ) {
            // 닉네임
            Text(
                text = user.nickName,
                style = MaterialTheme.typography.displayMedium,
            )

            // 여행일지 수
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // 여행일지 수
                LeadingIconTextComponent(
                    icon = R.drawable.icon_paper,
                    text = user.travelJournalCount.toString(),
                    textColor = Gray02,
                    iconColor = Gray02,
                )

                // 플레이스 수
                LeadingIconTextComponent(
                    icon = R.drawable.icon_pin,
                    text = user.placesCount.toString(),
                    textColor = Gray02,
                    iconColor = Gray02,
                )
            }
        }
    }
}

@Preview(name = "Test")
@Composable
private fun SearchUserItemTestPreview() {
    val user = SearchedUser(
        memberId = 1,
        nickName = "제주도",
        profileImageUrl = "",
        travelJournalCount = 23,
        placesCount = 78
    )
    TravelJournalForEveryoneTheme {
        Surface {
            SearchUserItem(
                user = user,
            )
        }
    }
}