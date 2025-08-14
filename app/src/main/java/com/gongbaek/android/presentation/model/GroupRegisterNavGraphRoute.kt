package com.gongbaek.android.presentation.model

import kotlinx.serialization.Serializable

sealed interface GroupRegisterNavGraphRoute : NavigationRoute {
    @Serializable
    data object GroupRegisterNavGraph : GroupRegisterNavGraphRoute

    @Serializable
    data object GroupCycle : GroupRegisterNavGraphRoute

    @Serializable
    data object SelectDay : GroupRegisterNavGraphRoute

    @Serializable
    data object SelectDayOfWeek : GroupRegisterNavGraphRoute

    @Serializable
    data object GroupTime : GroupRegisterNavGraphRoute

    @Serializable
    data object GroupCategory : GroupRegisterNavGraphRoute

    @Serializable
    data object GroupCover : GroupRegisterNavGraphRoute

    @Serializable
    data object GroupPlacePeople : GroupRegisterNavGraphRoute

    @Serializable
    data object GroupIntroduction : GroupRegisterNavGraphRoute

    @Serializable
    data object GroupRegister : GroupRegisterNavGraphRoute
}
