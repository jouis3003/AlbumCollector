package pt.techchallenge.albumcollector.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.techchallenge.albumcollector.ui.screens.detailsScreen.DetailsScreen
import pt.techchallenge.albumcollector.ui.screens.listScreen.ListScreen

@Composable
internal fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.ListScreen.name) {
        composable(Screens.ListScreen.name) {
            ListScreen(navController = navController)
        }

        composable(Screens.DetailsScreen.name) {
            DetailsScreen(navController = navController)
        }
    }
}

internal enum class Screens {
    ListScreen,
    DetailsScreen
}