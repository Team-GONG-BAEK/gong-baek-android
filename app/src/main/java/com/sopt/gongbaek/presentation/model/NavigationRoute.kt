package com.sopt.gongbaek.presentation.model

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object Splash : NavigationRoute

    @Serializable
    data object Onboarding : NavigationRoute

    @Serializable
    data class GroupDetail(
        val groupId: Int,
        val groupCycle: String
    ) : NavigationRoute

    @Serializable
    data class GroupRoom(
        val groupId: Int,
        val groupCycle: String
    ) : NavigationRoute
}
