package com.tedmoon99.presentation.common.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tedmoon99.presentation.log_in.view.LogInScreen
import com.tedmoon99.presentation.home.view.HomeScreen
import com.tedmoon99.presentation.common.screen.Screen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val snackbarHostState = SnackbarHostState()

    NavHost(
        navController = navController,
        startDestination = Screen.LogIn.route,
    ) {
        // Home
        composable(Screen.Home.route){
            HomeScreen()
        }

        // LogIn
        composable(Screen.LogIn.route){
            LogInScreen(
                hostState = snackbarHostState,
                onLogInSuccess = {

                }
            )
        }

    }
}