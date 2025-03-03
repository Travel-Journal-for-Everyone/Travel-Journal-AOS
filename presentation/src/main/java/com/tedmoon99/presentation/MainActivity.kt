package com.tedmoon99.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tedmoon99.presentation.navigation.MainNavigation
import com.tedmoon99.presentation.theme.TravelJournalForEveryoneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelJournalForEveryoneTheme {
                MainNavigation()
            }
        }
    }
}
