package com.sopt.gongbaek.presentation.ui.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.sopt.gongbaek.presentation.model.NavigationRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.AuthViewModel
import com.sopt.gongbaek.presentation.ui.auth.screen.CompleteAuthRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.EnterTimeTableRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.GapTimeTableRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.GenderRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.GradeRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.MajorSearchRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.MbtiRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.NicknameRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.SelectProfileRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.SelfIntroductionRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.TimetableConvertRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.UnivMajorRoute
import com.sopt.gongbaek.presentation.ui.auth.screen.UnivSearchRoute
import com.sopt.gongbaek.presentation.ui.home.navigation.navigateHomeNavGraph
import com.sopt.gongbaek.presentation.util.extension.sharedViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = NavigationRoute.AuthNavGraphRoute.SELECT_PROFILE,
        route = NavigationRoute.AuthNavGraphRoute.AUTH_NAV_GRAPH
    ) {
        composable(
            route = NavigationRoute.AuthNavGraphRoute.SELECT_PROFILE
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            SelectProfileRoute(
                viewModel = viewModel,
                navigateNickname = navController::navigateNickname
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.NICKNAME
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            NicknameRoute(
                viewModel = viewModel,
                navigateUnivMajor = navController::navigateUnivMajor,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.UNIV_MAJOR
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            UnivMajorRoute(
                viewModel = viewModel,
                navigateGrade = navController::navigateGrade,
                navigateUnivSearch = navController::navigateUnivSearch,
                navigateMajorSearch = navController::navigateMajorSearch,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.UNIV_SEARCH
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            UnivSearchRoute(
                viewModel = viewModel,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.MAJOR_SEARCH
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            MajorSearchRoute(
                viewModel = viewModel,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.GRADE
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            GradeRoute(
                viewModel = viewModel,
                navigateMbti = navController::navigateMbti,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.MBTI
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            MbtiRoute(
                viewModel = viewModel,
                navigateGender = navController::navigateGender,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.GENDER
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            GenderRoute(
                viewModel = viewModel,
                navigateSelfIntroduction = navController::navigateSelfIntroduction,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.SELF_INTRODUCTION
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            SelfIntroductionRoute(
                viewModel = viewModel,
                navigateEnterTimetable = navController::navigateEnterTimetable,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.ENTER_TIMETABLE
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            EnterTimeTableRoute(
                viewModel = viewModel,
                navigateTimetableConvert = navController::navigateTimetableConvert,
                navigateBack = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.TIMETABLE_CONVERT
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            TimetableConvertRoute(
                viewModel = viewModel,
                navigateGapTimeTable = navController::navigateGapTimetable
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.GAP_TIMETABLE
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            GapTimeTableRoute(
                viewModel = viewModel,
                navigateCompleteAuth = navController::navigateCompleteAuth,
                navigateEnterTimetable = navController::navigateEnterTimetable
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.TIMETABLE_CONVERT
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            TimetableConvertRoute(
                viewModel = viewModel,
                navigateGapTimeTable = navController::navigateGapTimetable
            )
        }

        composable(
            route = NavigationRoute.AuthNavGraphRoute.COMPLETE_AUTH
        ) { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            val navOptions = navOptions {
                popUpTo(NavigationRoute.ONBOARDING) {
                    inclusive = true
                }
            }
            CompleteAuthRoute(
                viewModel = viewModel,
                navigateHome = { navController.navigateHomeNavGraph(navOptions) }
            )
        }
    }
}
