package com.tedmoon99.presentation.util

sealed class Screen(
    val route: String,
){
    data object Home: Screen("Home")

}