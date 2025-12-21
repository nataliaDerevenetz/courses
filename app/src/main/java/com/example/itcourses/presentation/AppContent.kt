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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.itcourses.R
import com.example.itcourses.presentation.navigation.AppNavGraph
import com.example.main.navigation.MainBaseRoute
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
    if ( navBackStackEntry?.destination?.route == null) return
    val currentRoute = navBackStackEntry?.destination?.parent?.route
    val routes = remember { BottomTabs.entries.map { it.route.qualifiedName } }
    val selectedIcon = colorResource(id = R.color.green)

    if (currentRoute in routes) {
        NavigationBar {
            tabs.forEach { tab ->
                NavigationBarItem(
                    icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                    label = { Text(stringResource(tab.title)) },
                    selected = currentRoute == tab.route.qualifiedName,
                    onClick = {
                        if (tab.route.qualifiedName != currentRoute) {
                            navController.navigate(tab.route.qualifiedName!!) {
                                popUpTo(MainRoute::class.qualifiedName!!) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
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
}
