package com.sopt.gongbaek.presentation.ui.groupregister.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import com.sopt.gongbaek.presentation.model.GroupRegisterNavGraphRoute
import com.sopt.gongbaek.presentation.ui.grouplist.navigation.navigateGroupListNavGraph
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
    navigation<GroupRegisterNavGraphRoute.GroupRegisterNavGraph>(
        startDestination = GroupRegisterNavGraphRoute.GroupCycle
    ) {
        composable<GroupRegisterNavGraphRoute.GroupCycle> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupCycleRoute(
                viewModel = viewModel,
                navigateDay = navController::navigateSelectDay,
                navigateDayOfWeek = navController::navigateSelectDayOfWeek,
                navigateBack = navController::popBackStack
            )
        }

        composable<GroupRegisterNavGraphRoute.SelectDay> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            SelectDayRoute(
                viewModel = viewModel,
                navigateGroupTime = navController::navigateGroupTime,
                navigateBack = navController::popBackStack
            )
        }

        composable<GroupRegisterNavGraphRoute.SelectDayOfWeek> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            SelectDayOfWeekRoute(
                viewModel = viewModel,
                navigateGroupTime = navController::navigateGroupTime,
                navigateBack = navController::popBackStack
            )
        }

        composable<GroupRegisterNavGraphRoute.GroupTime> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupTimeRoute(
                viewModel = viewModel,
                navigateGroupCategory = navController::navigateGroupCategory,
                navigateBack = navController::popBackStack
            )
        }

        composable<GroupRegisterNavGraphRoute.GroupCategory> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupCategoryRoute(
                viewModel = viewModel,
                navigateGroupCover = navController::navigateGroupCover,
                navigateBack = navController::popBackStack
            )
        }

        composable<GroupRegisterNavGraphRoute.GroupCover> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupCoverRoute(
                viewModel = viewModel,
                navigateGroupPlacePeople = navController::navigateGroupPlacePeople,
                navigateBack = navController::popBackStack
            )
        }

        composable<GroupRegisterNavGraphRoute.GroupPlacePeople> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupPlacePeopleRoute(
                viewModel = viewModel,
                navigateGroupIntroduction = navController::navigateGroupIntroduction,
                navigateBack = navController::popBackStack
            )
        }

        composable<GroupRegisterNavGraphRoute.GroupIntroduction> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            GroupIntroductionRoute(
                viewModel = viewModel,
                navigateRegister = navController::navigateGroupRegister,
                navigateBack = navController::popBackStack
            )
        }

        composable<GroupRegisterNavGraphRoute.GroupRegister> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<GroupRegisterViewModel>(navController)
            val navOptions = navOptions {
                popUpTo(GroupRegisterNavGraphRoute.GroupRegisterNavGraph) {
                    inclusive = true
                }
            }
            GroupRegisterRoute(
                viewModel = viewModel,
                navigateMyGroup = { navController.navigateGroupListNavGraph(navOptions) },
                navigateBack = navController::popBackStack
            )
        }
    }
}
