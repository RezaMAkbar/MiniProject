package org.d3if3125.assessment3.navigation

import org.d3if3125.assessment3.view.BOARD_KEY_ID


sealed class Screen (val route : String) {
    data object Home : Screen("mainScreen")
    data object DetailedBoard : Screen("detailedBoard/{$BOARD_KEY_ID}"){
        fun withId(id: Int) = "detailedBoard/$id"
    }

}