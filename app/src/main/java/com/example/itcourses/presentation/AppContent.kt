package com.example.itcourses.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.itcourses.R
import com.example.itcourses.presentation.navigation.AppNavGraph
import com.example.main.navigation.MainRoute

@Composable
fun AppContent()
{
    val tabs = remember { BottomTabs.entries.toTypedArray() }
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController, tabs) }
    ) { innerPaddingModifier ->
        Box(Modifier.padding(innerPaddingModifier)){
            AppNavGraph(
                navController = navController,
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavController, tabs: Array<BottomTabs>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination

    val currentTab = tabs.find { tab ->
        destination?.hasRoute(tab.route::class) ?: false ||
                destination?.parent?.hasRoute(tab.route::class) ?: false
    }

    if (currentTab == null) return

    val selectedIcon = colorResource(id = R.color.green)

    NavigationBar {
        tabs.forEach { tab ->
            val isSelected = destination?.hasRoute(tab.route::class) ?: false ||
                    destination?.parent?.hasRoute(tab.route::class) ?: false

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(tab.route) {
                            popUpTo(MainRoute) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                label = { Text(stringResource(tab.title)) },
                alwaysShowLabel = true,
                modifier = Modifier.navigationBarsPadding(),
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedIcon,
                    unselectedIconColor = Color.White,
                    selectedTextColor = selectedIcon,
                    unselectedTextColor = Color.White
                )
            )
        }
    }
}