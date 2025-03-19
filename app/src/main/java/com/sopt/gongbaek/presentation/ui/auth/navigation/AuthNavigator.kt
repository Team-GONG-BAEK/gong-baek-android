package com.sopt.gongbaek.presentation.ui.auth.navigation

import androidx.navigation.NavController
import com.sopt.gongbaek.presentation.model.AuthNavGraphRoute

fun NavController.navigateAuthRoute() = navigate(AuthNavGraphRoute.AuthNavGraph)

fun NavController.navigateNickname() = navigate(AuthNavGraphRoute.Nickname)

fun NavController.navigateUnivMajor() = navigate(AuthNavGraphRoute.UnivMajor)

fun NavController.navigateUnivSearch() = navigate(AuthNavGraphRoute.UnivSearch)

fun NavController.navigateMajorSearch() = navigate(AuthNavGraphRoute.MajorSearch)

fun NavController.navigateGrade() = navigate(AuthNavGraphRoute.Grade)

fun NavController.navigateMbti() = navigate(AuthNavGraphRoute.Mbti)

fun NavController.navigateGender() = navigate(AuthNavGraphRoute.Gender)

fun NavController.navigateSelfIntroduction() = navigate(AuthNavGraphRoute.SelfIntroduction)

fun NavController.navigateEnterTimetable() = navigate(AuthNavGraphRoute.EnterTimetable)

fun NavController.navigateGapTimetable() = navigate(AuthNavGraphRoute.GapTimetable)

fun NavController.navigateTimetableConvert() = navigate(AuthNavGraphRoute.TimetableConvert)

fun NavController.navigateCompleteAuth() = navigate(AuthNavGraphRoute.CompleteAuth)
