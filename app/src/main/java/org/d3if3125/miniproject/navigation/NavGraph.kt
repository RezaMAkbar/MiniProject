package org.d3if3125.miniproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3125.miniproject.ui.screen.AboutScreen
import org.d3if3125.miniproject.ui.screen.LengthScreen
import org.d3if3125.miniproject.ui.screen.BmiScreen
import org.d3if3125.miniproject.ui.screen.MainScreen
import org.d3if3125.miniproject.ui.screen.NOTE_KEY_ID
import org.d3if3125.miniproject.ui.screen.NoteDetailScreen
import org.d3if3125.miniproject.ui.screen.NoteScreen
import org.d3if3125.miniproject.ui.screen.SpeedScreen
import org.d3if3125.miniproject.ui.screen.TemperatureScreen
import org.d3if3125.miniproject.ui.screen.WeightScreen

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
        composable(route = Screen.Length.route) {
            LengthScreen(navController)
        }
        composable(route = Screen.Weight.route) {
            WeightScreen(navController)
        }
        composable(route = Screen.Speed.route) {
            SpeedScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Bmi.route) {
            BmiScreen(navController)
        }
        composable(route = Screen.Note.route) {
            NoteScreen(navController)
        }
        composable(route = Screen.AddNote.route) {
            NoteDetailScreen(navController)
        }
        composable(
            route = Screen.EditNote.route,
            arguments = listOf(
                navArgument(NOTE_KEY_ID) { type = NavType.LongType }
            )
        ) {
                navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(NOTE_KEY_ID)
            NoteDetailScreen(navController, id)
        }

    }
}