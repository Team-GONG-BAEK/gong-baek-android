package com.sopt.gongbaek.presentation.ui.setting.navigation

import androidx.navigation.NavController
import com.sopt.gongbaek.presentation.model.NavigationRoute

fun NavController.navigateSetting(){
    navigate(
        NavigationRoute.Setting
    )
}