package com.sopt.gongbaek.presentation.model

import kotlinx.serialization.Serializable

sealed interface MainBottomTabRoute : NavigationRoute {
    @Serializable
    data object GroupList : MainBottomTabRoute

    @Serializable
    data object Home : MainBottomTabRoute

    @Serializable
    data object MyPage : MainBottomTabRoute
}
