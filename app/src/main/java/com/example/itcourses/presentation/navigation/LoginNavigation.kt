package com.example.itcourses.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.itcourses.presentation.LoginScreen
import com.example.utils.navigation.BottomRoute
import kotlinx.serialization.Serializable


@Serializable object LoginRoute : BottomRoute

@Serializable object LoginBaseRoute : BottomRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(route = LoginRoute, navOptions)


fun NavGraphBuilder.login(
    onNavigateToMain: () -> Unit,
) {
    navigation<LoginBaseRoute>(startDestination = LoginRoute) {
        composable<LoginRoute> {
            LoginScreen(onNavigateToMain)
        }
    }
}
