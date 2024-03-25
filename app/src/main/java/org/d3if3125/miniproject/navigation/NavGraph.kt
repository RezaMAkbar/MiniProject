package org.d3if3125.miniproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if3125.miniproject.ui.screen.MainScreen
import org.d3if3125.miniproject.ui.screen.TemperatureScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.Temp.route) {
            TemperatureScreen(navController)
        }
        composable(route = Screen.About.route) {
            TemperatureScreen(navController)
        }
    }
}