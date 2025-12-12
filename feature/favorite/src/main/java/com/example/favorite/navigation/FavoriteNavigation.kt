package com.example.favorite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.favorite.FavoriteScreen
import kotlinx.serialization.Serializable

@Serializable object FavoriteRoute

@Serializable data object FavoriteBaseRoute


fun NavController.navigateToFavorite(navOptions: NavOptions? = null) =
    navigate(route = FavoriteRoute) {
        launchSingleTop = true
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
    }


fun NavGraphBuilder.favorite(
) {
    navigation<FavoriteBaseRoute>(startDestination = FavoriteRoute) {
        composable<FavoriteRoute> {
            FavoriteScreen()
        }
    }
}
