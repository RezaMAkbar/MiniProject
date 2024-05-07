package org.d3if3125.miniproject.navigation

sealed class Screen (val route : String) {
    data object Home : Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object Temp: Screen("tempScreen")
    data object Weight: Screen("weightScreen")
    data object Length: Screen("lengthScreen")
    data object Speed: Screen("speedScreen")
    data object Bmi: Screen("bmiScreen")
}