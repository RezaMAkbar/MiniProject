package org.d3if3125.assessment3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3125.assessment3.navigation.Screen
import org.d3if3125.assessment3.view.BOARD_KEY_ID
import org.d3if3125.assessment3.view.BoardDetailed
import org.d3if3125.assessment3.view.MainDashBoard


@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainDashBoard(navController)
        }
        composable(
            route = Screen.DetailedBoard.route,
            arguments = listOf(navArgument(BOARD_KEY_ID) { type = NavType.IntType })
        ) {  navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(BOARD_KEY_ID)
            BoardDetailed(navController, id)
        }
    }
}