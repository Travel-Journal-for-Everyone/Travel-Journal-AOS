package com.tedmoon99.presentation.common.screen

sealed class Screen(
    val route: String,
){
    data object Home: Screen("Home")

}