package com.example.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.main.MainScreen
import kotlinx.serialization.Serializable

@Serializable object MainRoute

@Serializable data object MainBaseRoute

fun NavController.navigateToMain(navOptions: NavOptions? = null) =
    navigate(route = MainRoute){
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
    }


fun NavGraphBuilder.main() {
    navigation<MainBaseRoute>(startDestination = MainRoute) {
        composable<MainRoute> {
            MainScreen()
        }
    }
}