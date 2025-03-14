package com.tedmoon99.presentation.common.screen

sealed class Screen(
    val route: String,
){
    data object Home: Screen("Home")

    data object LogIn: Screen("LogIn")

    data object WriteProfile: Screen("WriteProfile")

    data object Welcome: Screen("Welcome/{name}")

}