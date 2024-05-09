package org.d3if3125.miniproject.navigation

import org.d3if3125.miniproject.ui.screen.NOTE_KEY_ID

sealed class Screen (val route : String) {
    data object Home : Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object Temp: Screen("tempScreen")
    data object Weight: Screen("weightScreen")
    data object Length: Screen("lengthScreen")
    data object Speed: Screen("speedScreen")
    data object Bmi: Screen("bmiScreen")
    data object Note: Screen("noteScreen")
    data object AddNote: Screen("detailScreen")
    data object EditNote: Screen("detailScreen/{$NOTE_KEY_ID}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}