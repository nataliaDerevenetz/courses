package com.example.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.main.MainScreen
import com.example.utils.navigation.BottomRoute
import kotlinx.serialization.Serializable

@Serializable object MainRoute : BottomRoute

@Serializable data object MainBaseRoute : BottomRoute

fun NavController.navigateToMain(navOptions: NavOptions? = null) = navigate(route = MainBaseRoute, navOptions)

fun NavGraphBuilder.main() {
    navigation<MainBaseRoute>(startDestination = MainRoute) {
        composable<MainRoute> {
            MainScreen()
        }
    }
}