package com.sopt.gongbaek.presentation.ui.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.sopt.gongbaek.presentation.model.AuthNavGraphRoute
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
    navigation<AuthNavGraphRoute.AuthNavGraph>(
        startDestination = AuthNavGraphRoute.SelectProfile
    ) {
        composable<AuthNavGraphRoute.SelectProfile> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            SelectProfileRoute(
                viewModel = viewModel,
                navigateNickname = navController::navigateNickname
            )
        }

        composable<AuthNavGraphRoute.Nickname> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            NicknameRoute(
                viewModel = viewModel,
                navigateUnivMajor = navController::navigateUnivMajor,
                navigateBack = navController::popBackStack
            )
        }

        composable<AuthNavGraphRoute.UnivMajor> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            UnivMajorRoute(
                viewModel = viewModel,
                navigateGrade = navController::navigateGrade,
                navigateUnivSearch = navController::navigateUnivSearch,
                navigateMajorSearch = navController::navigateMajorSearch,
                navigateBack = navController::popBackStack
            )
        }

        composable<AuthNavGraphRoute.UnivSearch> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            UnivSearchRoute(
                viewModel = viewModel,
                navigateBack = navController::popBackStack
            )
        }

        composable<AuthNavGraphRoute.MajorSearch> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            MajorSearchRoute(
                viewModel = viewModel,
                navigateBack = navController::popBackStack
            )
        }

        composable<AuthNavGraphRoute.Grade> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            GradeRoute(
                viewModel = viewModel,
                navigateMbti = navController::navigateMbti,
                navigateBack = navController::popBackStack
            )
        }

        composable<AuthNavGraphRoute.Mbti> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            MbtiRoute(
                viewModel = viewModel,
                navigateGender = navController::navigateGender,
                navigateBack = navController::popBackStack
            )
        }

        composable<AuthNavGraphRoute.Gender> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            GenderRoute(
                viewModel = viewModel,
                navigateSelfIntroduction = navController::navigateSelfIntroduction,
                navigateBack = navController::popBackStack
            )
        }

        composable<AuthNavGraphRoute.SelfIntroduction> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            SelfIntroductionRoute(
                viewModel = viewModel,
                navigateEnterTimetable = navController::navigateEnterTimetable,
                navigateBack = navController::popBackStack
            )
        }

        composable<AuthNavGraphRoute.EnterTimetable> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            EnterTimeTableRoute(
                viewModel = viewModel,
                navigateTimetableConvert = navController::navigateTimetableConvert,
                navigateBack = navController::popBackStack
            )
        }

        composable<AuthNavGraphRoute.TimetableConvert> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            TimetableConvertRoute(
                viewModel = viewModel,
                navigateGapTimeTable = navController::navigateGapTimetable
            )
        }

        composable<AuthNavGraphRoute.GapTimetable> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            GapTimeTableRoute(
                viewModel = viewModel,
                navigateCompleteAuth = navController::navigateCompleteAuth,
                navigateEnterTimetable = navController::navigateEnterTimetable
            )
        }

        composable<AuthNavGraphRoute.CompleteAuth> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            val navOptions = navOptions {
                popUpTo(NavigationRoute.Onboarding) {
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
