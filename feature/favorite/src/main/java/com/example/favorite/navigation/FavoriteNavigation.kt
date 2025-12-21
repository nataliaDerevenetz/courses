package com.example.favorite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.favorite.FavoriteScreen
import com.example.utils.navigation.BottomRoute
import kotlinx.serialization.Serializable

@Serializable object FavoriteRoute: BottomRoute

@Serializable data object FavoriteBaseRoute : BottomRoute


fun NavController.navigateToFavorite(navOptions: NavOptions? = null) = navigate(route = FavoriteRoute, navOptions)


fun NavGraphBuilder.favorite(
) {
    navigation<FavoriteBaseRoute>(startDestination = FavoriteRoute) {
        composable<FavoriteRoute> {
            FavoriteScreen()
        }
    }
}
