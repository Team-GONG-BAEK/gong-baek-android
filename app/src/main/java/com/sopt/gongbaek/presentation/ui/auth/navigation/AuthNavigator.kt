package com.sopt.gongbaek.presentation.ui.auth.navigation

import androidx.navigation.NavController
import com.sopt.gongbaek.presentation.model.AuthNavGraphRoute

fun NavController.navigateAuthRoute() = navigate(AuthNavGraphRoute.AuthNavGraph)

fun NavController.navigateUnivSearch() = navigate(AuthNavGraphRoute.UnivSearch)

fun NavController.navigateMajorSearch() = navigate(AuthNavGraphRoute.MajorSearch)

fun NavController.navigateEmailVerification() = navigate(AuthNavGraphRoute.EmailVerification)

fun NavController.navigateNicknameGender() = navigate(AuthNavGraphRoute.NicknameGender)

fun NavController.navigateSelectProfile() = navigate(AuthNavGraphRoute.SelectProfile)

fun NavController.navigateMbti() = navigate(AuthNavGraphRoute.Mbti)

fun NavController.navigateSelfIntroduction() = navigate(AuthNavGraphRoute.SelfIntroduction)

fun NavController.navigateEnterTimetable() = navigate(AuthNavGraphRoute.EnterTimetable)

fun NavController.navigateCompleteAuth() = navigate(AuthNavGraphRoute.CompleteAuth)
