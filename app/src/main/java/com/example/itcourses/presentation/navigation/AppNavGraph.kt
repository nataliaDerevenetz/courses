package com.example.itcourses.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.example.favorite.navigation.favorite
import com.example.main.navigation.MainBaseRoute
import com.example.main.navigation.main
import com.example.main.navigation.navigateToMain
import com.example.profile.profile
import kotlinx.serialization.Serializable

@Serializable
object TabsBaseRoute

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = LoginBaseRoute::class
    ) {
        login(onNavigateToMain = {navController.navigateToMain(navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
        })})

        navigation<TabsBaseRoute>(startDestination = MainBaseRoute) {
            main()
            favorite()
            profile()
        }
    }
}
