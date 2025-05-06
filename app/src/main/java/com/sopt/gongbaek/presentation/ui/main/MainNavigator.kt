package com.sopt.gongbaek.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.type.MainBottomNavBarTabType
import com.sopt.gongbaek.presentation.ui.grouplist.navigation.navigateGroupListNavGraph
import com.sopt.gongbaek.presentation.ui.home.navigation.navigateHomeNavGraph
import com.sopt.gongbaek.presentation.ui.mypage.navigation.navigateMyPage

class MainNavigator(
    val navController: NavHostController
) {
    val startDestination = NavigationRoute.Splash

    val currentMainBottomNavBarTab: MainBottomNavBarTabType?
        @Composable get() = MainBottomNavBarTabType.find { tab -> currentDestination?.hasRoute(tab::class) == true }

    private val previousDestination = mutableStateOf<NavDestination?>(null)

    private val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow.collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    fun navigate(mainBottomNavBarTabType: MainBottomNavBarTabType) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (mainBottomNavBarTabType) {
            MainBottomNavBarTabType.GROUP_LIST -> navController.navigateGroupListNavGraph(navOptions)
            MainBottomNavBarTabType.HOME -> navController.navigateHomeNavGraph(navOptions)
//            MainBottomNavBarTabType.TIMETABLE -> {}
            MainBottomNavBarTabType.MY_PAGE -> navController.navigateMyPage(navOptions)
        }
    }

    @Composable
    fun showBottomBar() = MainBottomNavBarTabType.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController = navController)
}
