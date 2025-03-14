package com.tedmoon99.presentation.common.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tedmoon99.presentation.log_in.view.LogInScreen
import com.tedmoon99.presentation.home.view.HomeScreen
import com.tedmoon99.presentation.common.screen.Screen
import com.tedmoon99.presentation.log_in.view.WelcomeScreen
import com.tedmoon99.presentation.log_in.view.WriteProfileScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val snackbarHostState = SnackbarHostState()

    NavHost(
        navController = navController,
        startDestination = Screen.LogIn.route,
    ) {
        // Home
        composable(
            route = Screen.Home.route,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }
        ){
            HomeScreen()
        }

        // LogIn
        composable(
            route = Screen.LogIn.route,
            exitTransition = { slideOutHorizontally() }
        ){
            LogInScreen(
                hostState = snackbarHostState,
                navigateToHome = {
                    navController.navigate(Screen.Home.route){
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                navigateToWriteProfile = {
                    navController.navigate(Screen.WriteProfile.route)
                }
            )
        }

        // WriteProfile
        composable(
            route = Screen.WriteProfile.route,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }
        ){
            WriteProfileScreen(
                hostState = snackbarHostState,
                navigateToWelcome = { name ->
                    // Welcome 페이지로 이동
                    navController.navigate(
                        Screen.Welcome.route.replace("{name}", name)
                    ){
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }

        // Welcome
        composable(
            route = Screen.Welcome.route,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType }
            ),
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }
        ){ navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString("name") ?: ""

            WelcomeScreen(
                name = name,
                navigateToHome = {
                    navController.navigate(Screen.Home.route){
                        popUpTo(navController.graph.id){ inclusive = true }
                    }
                }
            )
        }
    }
}