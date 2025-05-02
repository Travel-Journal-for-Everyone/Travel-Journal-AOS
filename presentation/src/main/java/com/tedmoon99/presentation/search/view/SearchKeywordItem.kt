package com.tedmoon99.presentation.search.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tedmoon99.presentation.common.theme.Gray02

@Composable
fun SearchKeywordItem(
    keyword: String,
    onItemClicked: (String) -> Unit,
    onDeleteClicked: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable { onItemClicked(keyword) }.padding(vertical = 16.dp, horizontal = 10.dp),
    ) {
        // Keyword
        Text(
            text = keyword,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.weight(1f)) // 빈 공간

        // X 버튼
        Icon(
            imageVector = Icons.Default.Close,
            modifier = Modifier.size(20.dp).clickable { onDeleteClicked(keyword) },
            tint = Gray02,
            contentDescription = "Close",
        )
    }
}