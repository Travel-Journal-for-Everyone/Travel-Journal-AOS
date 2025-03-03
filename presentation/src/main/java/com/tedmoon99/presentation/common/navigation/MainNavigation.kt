package com.tedmoon99.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tedmoon99.presentation.home.HomeScreen
import com.tedmoon99.presentation.common.screen.Screen

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