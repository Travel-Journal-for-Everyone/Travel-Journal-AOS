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
import com.tedmoon99.presentation.common.screen.Screen
import com.tedmoon99.presentation.follow.view.FollowScreen
import com.tedmoon99.presentation.home.view.HomeScreen
import com.tedmoon99.presentation.log_in.view.LogInScreen
import com.tedmoon99.presentation.log_in.view.WelcomeScreen
import com.tedmoon99.presentation.log_in.view.WriteProfileScreen
import com.tedmoon99.presentation.search.view.SearchInputScreen
import com.tedmoon99.presentation.search.view.SearchScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val snackBarHostState = SnackbarHostState()

    NavHost(
        navController = navController,
        startDestination = Screen.LogIn.route,
    ) {
        // Home
        composable(
            route = Screen.Home.route,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() },
        ) {
            HomeScreen()
        }

        // LogIn
        composable(
            route = Screen.LogIn.route,
            exitTransition = { slideOutHorizontally() }
        ) {
            LogInScreen(
                hostState = snackBarHostState,
                navigateToHome = {
                    navController.navigate(Screen.Home.route) {
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
        ) {
            WriteProfileScreen(
                hostState = snackBarHostState,
                navigateToWelcome = { name ->
                    // Welcome 페이지로 이동
                    navController.navigate(
                        Screen.Welcome.route.replace("{name}", name)
                    ) {
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
        ) { navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString("name") ?: ""

            WelcomeScreen(
                name = name,
                navigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }

        // Search
        composable(
            route = Screen.Search.route,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }
        ) { navBackStackEntry ->

            val savedStateHandle = navBackStackEntry.savedStateHandle
            val keywordFlow = savedStateHandle.getStateFlow("keyword", "")

            SearchScreen(
                hostState = snackBarHostState,
                keywordFlow = keywordFlow,
                navigateToSearchInput = { keyword ->
                    // 키워드 저장
                    navController.currentBackStackEntry?.savedStateHandle?.set("keyword", keyword)
                    // 화면 전환
                    navController.navigate(Screen.SearchInput.route)

                },
                navigateToDiaryItemDetail = { diaryId ->

                },
                navigateToUserItemDetail = { memberId ->

                },
                navigateToPlaceItemDetail = { placeId ->

                }
            )
        }

        // SearchInput
        composable(Screen.SearchInput.route){ navBackStackEntry ->

            SearchInputScreen(
                hostState = snackBarHostState,
                navigateToSearch = { keyword ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("keyword",keyword)
                    navController.popBackStack()
                },
            )
        }

        // Follow
        composable(Screen.Follow.route){navBackStackEntry  ->

            FollowScreen(
                navigateToBack = {
                    navController.popBackStack()
                },
                navigateToOtherProfile = { memberId: Int ->
                    TODO("다른 사용자의 프로필로 이동")
                }
            )
        }

    }
}