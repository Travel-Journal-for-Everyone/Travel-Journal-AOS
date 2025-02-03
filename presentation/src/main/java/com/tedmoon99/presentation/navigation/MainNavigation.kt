package com.tedmoon99.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tedmoon99.presentation.ui.screens.HomeScreen
import com.tedmoon99.presentation.util.Screen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        // Home
        composable(Screen.Home.route){
            HomeScreen()
        }
    }
}