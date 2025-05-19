package com.tedmoon99.presentation.follow.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tedmoon99.presentation.R
import com.tedmoon99.presentation.common.components.button.ButtonComponent
import com.tedmoon99.presentation.common.components.button.OutlinedButtonComponent
import com.tedmoon99.presentation.common.components.image.CircleCoilImageComponent
import com.tedmoon99.presentation.common.components.text.LeadingIconTextComponent
import com.tedmoon99.presentation.common.theme.Black
import com.tedmoon99.presentation.common.theme.Gray02
import com.tedmoon99.presentation.common.theme.TravelJournalForEveryoneTheme

@Composable
fun FollowRequestListItem(
    nickName: String,
    profileImageUrl: String,
    diaryCount: Int,
    placeCount: Int,
    modifier: Modifier = Modifier,
    onApproveClicked: () -> Unit,
    onDenyClicked: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(),
    ) {
        // ProfileImage
        CircleCoilImageComponent(
            profileImageUrl = profileImageUrl,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .height(52.dp)
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            // NickName
            Text(
                text = nickName,
                style = MaterialTheme.typography.titleSmall,
                color = Black,
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Diary, Place
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                // Diary
                LeadingIconTextComponent(
                    text = diaryCount.toString(),
                    icon = R.drawable.icon_paper,
                    textColor = Gray02,
                    iconColor = Gray02,
                )

                // Place
                LeadingIconTextComponent(
                    text = placeCount.toString(),
                    icon = R.drawable.icon_pin,
                    textColor = Gray02,
                    iconColor = Gray02,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Approve
        ButtonComponent(
            label = stringResource(R.string.label_approve),
            buttonShape = RoundedCornerShape(8.dp),
            enabled = true,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 4.dp),
            onClick = onApproveClicked,
        )

        // Deny
        OutlinedButtonComponent(
            label = stringResource(R.string.label_deny),
            buttonShape = RoundedCornerShape(8.dp),
            enabled = true,
            modifier = Modifier
                .padding(vertical = 8.dp),
            onClick = onDenyClicked,
        )
    }
}


@Preview
@Composable
private fun FollowRequestListItemPreview() {
    val nickName = "여행자"
    val profileImageUrl = ""
    val diaryCount = 32
    val placeCount = 25


    TravelJournalForEveryoneTheme {
        Surface {
            FollowRequestListItem(
                nickName = nickName,
                profileImageUrl = profileImageUrl,
                diaryCount = diaryCount,
                placeCount = placeCount,
                onApproveClicked = {},
                onDenyClicked = {}
            )
        }
    }
}