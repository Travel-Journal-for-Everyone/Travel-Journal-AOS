package com.tedmoon99.presentation.ui.common.screen

sealed class Screen(
    val route: String,
){
    data object Home: Screen("Home")

}