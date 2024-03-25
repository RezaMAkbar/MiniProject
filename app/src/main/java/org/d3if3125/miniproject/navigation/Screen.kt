package org.d3if3125.miniproject.navigation

sealed class Screen (val route : String) {
    data object Home : Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object Temp: Screen("tempScreen")
}