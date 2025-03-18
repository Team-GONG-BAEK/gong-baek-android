package com.sopt.gongbaek.presentation.ui.groupregister.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.grouplist.navigation.navigateGroupList
import com.sopt.gongbaek.presentation.ui.groupregister.screen.GroupCategoryRoute
import com.sopt.gongbaek.presentation.ui.groupregister.screen.GroupCoverRoute
import com.sopt.gongbaek.presentation.ui.groupregister.screen.GroupCycleRoute
import com.sopt.gongbaek.presentation.ui.groupregister.screen.GroupIntroductionRoute
import com.sopt.gongbaek.presentation.ui.groupregister.screen.GroupPlacePeopleRoute
import com.sopt.gongbaek.presentation.ui.groupregister.screen.GroupRegisterRoute
import com.sopt.gongbaek.presentation.ui.groupregister.screen.GroupRegisterViewModel
import com.sopt.gongbaek.presentation.ui.groupregister.screen.GroupTimeRoute
import com.sopt.gongbaek.presentation.ui.groupregister.screen.SelectDayOfWeekRoute
import com.sopt.gongbaek.presentation.ui.groupregister.screen.SelectDayRoute
import com.sopt.gongbaek.presentation.util.extension.sharedViewModel

fun NavGraphBuilder.groupRegisterNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = NavigationRoute.GroupRegisterNavGraphRoute.GROUP_CYCLE,
        route = NavigationRoute.GroupRegisterNavGraphRoute.GROUP_REGISTER_NAV_GRAPH
    ) {
        composable(
            route = NavigationRoute.GroupRegisterNavGraphRoute.GROUP_CYCLE
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupCycleRoute(
                viewModel = viewModel,
                navigateDay = navController::navigateSelectDay,
                navigateDayOfWeek = navController::navigateSelectDayOfWeek,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.GroupRegisterNavGraphRoute.SELECT_DAY
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            SelectDayRoute(
                viewModel = viewModel,
                navigateGroupTime = navController::navigateGroupTime,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.GroupRegisterNavGraphRoute.SELECT_DAY_OF_WEEK
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            SelectDayOfWeekRoute(
                viewModel = viewModel,
                navigateGroupTime = navController::navigateGroupTime,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.GroupRegisterNavGraphRoute.GROUP_TIME
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupTimeRoute(
                viewModel = viewModel,
                navigateGroupCategory = navController::navigateGroupCategory,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.GroupRegisterNavGraphRoute.GROUP_CATEGORY
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupCategoryRoute(
                viewModel = viewModel,
                navigateGroupCover = navController::navigateGroupCover,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.GroupRegisterNavGraphRoute.GROUP_COVER
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupCoverRoute(
                viewModel = viewModel,
                navigateGroupPlacePeople = navController::navigateGroupPlacePeople,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.GroupRegisterNavGraphRoute.GROUP_PLACE_PEOPLE
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupPlacePeopleRoute(
                viewModel = viewModel,
                navigateGroupIntroduction = navController::navigateGroupIntroduction,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.GroupRegisterNavGraphRoute.GROUP_INTRODUCTION
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupIntroductionRoute(
                viewModel = viewModel,
                navigateRegister = navController::navigateGroupRegister,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.GroupRegisterNavGraphRoute.GROUP_REGISTER
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupRegisterRoute(
                viewModel = viewModel,
                navigateMyGroup = navController::navigateGroupList,
                navigateBack = navController::popBackStack
            )
        }
    }
}
