package com.gongbaek.android.presentation.ui.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.gongbaek.android.presentation.model.AuthNavGraphRoute
import com.gongbaek.android.presentation.model.NavigationRoute
import com.gongbaek.android.presentation.ui.auth.screen.AcademicInfoRoute
import com.gongbaek.android.presentation.ui.auth.screen.AuthViewModel
import com.gongbaek.android.presentation.ui.auth.screen.CompleteAuthRoute
import com.gongbaek.android.presentation.ui.auth.screen.EmailVerificationRoute
import com.gongbaek.android.presentation.ui.auth.screen.EnterTimeTableRoute
import com.gongbaek.android.presentation.ui.auth.screen.MajorSearchRoute
import com.gongbaek.android.presentation.ui.auth.screen.MbtiRoute
import com.gongbaek.android.presentation.ui.auth.screen.NicknameGenderRoute
import com.gongbaek.android.presentation.ui.auth.screen.SelectProfileRoute
import com.gongbaek.android.presentation.ui.auth.screen.SelfIntroductionRoute
import com.gongbaek.android.presentation.ui.auth.screen.UnivSearchRoute
import com.gongbaek.android.presentation.ui.home.navigation.navigateHomeNavGraph
import com.gongbaek.android.presentation.util.extension.sharedViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    navigateBack: () -> Unit
) {
    navigation<AuthNavGraphRoute.AuthNavGraph>(
        startDestination = AuthNavGraphRoute.AcademicInfo
    ) {
        composable<AuthNavGraphRoute.AcademicInfo> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            AcademicInfoRoute(
                viewModel = viewModel,
                navigateEmailVerification = navController::navigateEmailVerification,
                navigateUnivSearch = navController::navigateUnivSearch,
                navigateMajorSearch = navController::navigateMajorSearch,
                navigateBack = navigateBack
            )
        }

        composable<AuthNavGraphRoute.UnivSearch> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            UnivSearchRoute(
                viewModel = viewModel,
                navigateBack = navigateBack
            )
        }

        composable<AuthNavGraphRoute.MajorSearch> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            MajorSearchRoute(
                viewModel = viewModel,
                navigateBack = navigateBack
            )
        }

        composable<AuthNavGraphRoute.EmailVerification> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            EmailVerificationRoute(
                viewModel = viewModel,
                navigateNicknameGender = navController::navigateNicknameGender,
                navigateBack = navigateBack
            )
        }

        composable<AuthNavGraphRoute.NicknameGender> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            NicknameGenderRoute(
                viewModel = viewModel,
                navigateSelectProfile = navController::navigateSelectProfile,
                navigateBack = navigateBack
            )
        }

        composable<AuthNavGraphRoute.SelectProfile> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            SelectProfileRoute(
                viewModel = viewModel,
                navigateMbti = navController::navigateMbti,
                navigateBack = navigateBack
            )
        }

        composable<AuthNavGraphRoute.Mbti> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            MbtiRoute(
                viewModel = viewModel,
                navigateSelfIntroduction = navController::navigateSelfIntroduction,
                navigateBack = navigateBack
            )
        }

        composable<AuthNavGraphRoute.SelfIntroduction> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            SelfIntroductionRoute(
                viewModel = viewModel,
                navigateEnterTimetable = navController::navigateEnterTimetable,
                navigateBack = navigateBack
            )
        }

        composable<AuthNavGraphRoute.EnterTimetable> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            EnterTimeTableRoute(
                viewModel = viewModel,
                navigateCompleteAuth = navController::navigateCompleteAuth,
                navigateBack = navigateBack
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
