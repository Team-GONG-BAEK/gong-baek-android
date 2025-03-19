package com.sopt.gongbaek.presentation.ui.groupregister.navigation

import androidx.navigation.NavController
import com.sopt.gongbaek.presentation.model.GroupRegisterNavGraphRoute

fun NavController.navigateGroupRegisterNavGraph() = navigate(GroupRegisterNavGraphRoute.GroupRegisterNavGraph)

fun NavController.navigateGroupCycle() = navigate(GroupRegisterNavGraphRoute.GroupCycle)

fun NavController.navigateSelectDay() = navigate(GroupRegisterNavGraphRoute.SelectDay)

fun NavController.navigateSelectDayOfWeek() = navigate(GroupRegisterNavGraphRoute.SelectDayOfWeek)

fun NavController.navigateGroupTime() = navigate(GroupRegisterNavGraphRoute.GroupTime)

fun NavController.navigateGroupCategory() = navigate(GroupRegisterNavGraphRoute.GroupCategory)

fun NavController.navigateGroupCover() = navigate(GroupRegisterNavGraphRoute.GroupCover)

fun NavController.navigateGroupPlacePeople() = navigate(GroupRegisterNavGraphRoute.GroupPlacePeople)

fun NavController.navigateGroupIntroduction() = navigate(GroupRegisterNavGraphRoute.GroupIntroduction)

fun NavController.navigateGroupRegister() = navigate(GroupRegisterNavGraphRoute.GroupRegister)
