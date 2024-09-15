package com.fahad.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fahad.moviesapp.navigation.AppDestinations.DETAILS_ROUTE
import com.fahad.moviesapp.navigation.AppDestinationsArgs.ID_ARGS
import com.fahad.moviesapp.ui.screens.movies.details.DetailsScreen
import com.fahad.moviesapp.ui.screens.movies.list.MoviesScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.Movies.name,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
) {
    NavHost(navController = navController, startDestination = startDestination, modifier) {
        composable(Screens.Movies.name) {
            MoviesScreen(
                goToDetails = navActions::navigateToDetails
            )
        }

        composable(
            DETAILS_ROUTE,
            arguments = listOf(navArgument(ID_ARGS) {
                type = IntType
            })
        ) {
            val id = it.arguments?.getInt(ID_ARGS) ?: 0
            DetailsScreen(id = id)
        }
    }
}