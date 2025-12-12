package com.example.itcourses.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.favorite.navigation.favorite
import com.example.main.navigation.main
import com.example.main.navigation.navigateToMain
import com.example.profile.profile


@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = LoginBaseRoute::class
    ) {
        login(onNavigateToMain = navController::navigateToMain)

        main()

        favorite()

        profile()
    }
}
