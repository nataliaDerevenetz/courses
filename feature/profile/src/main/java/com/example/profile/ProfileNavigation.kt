package com.example.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable object ProfileRoute

@Serializable data object ProfileBaseRoute

fun NavController.navigateToProfile(navOptions: NavOptions? = null) =
    navigate(route = ProfileRoute, navOptions)


fun NavGraphBuilder.profile() {
    navigation<ProfileBaseRoute>(startDestination = ProfileRoute) {
        composable<ProfileRoute> {
            ProfileScreen()
        }
    }
}