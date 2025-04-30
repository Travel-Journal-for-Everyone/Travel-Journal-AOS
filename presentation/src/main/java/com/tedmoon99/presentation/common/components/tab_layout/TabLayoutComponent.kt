package com.tedmoon99.presentation.common.components.tab_layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.tedmoon99.presentation.common.theme.TravelJournalForEveryoneTheme

@Composable
fun TabLayoutComponent(
    selectedTab: Int,
    tabs: List<String>,
    onTabClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TabRow(
        selectedTabIndex = selectedTab,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabClicked(index) },
                text = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        color = if (selectedTab == index) Color.Black else Color.Gray
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray
            )
        }

    }
}

@Preview
@Composable
private fun TabLayoutPreview() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("여행일지", "플레이스", "여행자")

    TravelJournalForEveryoneTheme {
        Surface {
            TabLayoutComponent(
                selectedTab = selectedTab,
                tabs = tabs,
                onTabClicked = {
                    selectedTab = it
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}