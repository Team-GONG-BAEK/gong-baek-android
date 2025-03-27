package com.sopt.gongbaek.presentation.model

import kotlinx.serialization.Serializable

sealed interface AuthNavGraphRoute : NavigationRoute {
    @Serializable
    data object AuthNavGraph : AuthNavGraphRoute

    @Serializable
    data object SelectProfile : AuthNavGraphRoute

    @Serializable
    data object NicknameGender : AuthNavGraphRoute

    @Serializable
    data object AcademicInfo : AuthNavGraphRoute

    @Serializable
    data object EmailVerification : AuthNavGraphRoute

    @Serializable
    data object Grade : AuthNavGraphRoute

    @Serializable
    data object Mbti : AuthNavGraphRoute

    @Serializable
    data object Gender : AuthNavGraphRoute

    @Serializable
    data object SelfIntroduction : AuthNavGraphRoute

    @Serializable
    data object EnterTimetable : AuthNavGraphRoute

    @Serializable
    data object GapTimetable : AuthNavGraphRoute

    @Serializable
    data object CompleteAuth : AuthNavGraphRoute

    @Serializable
    data object UnivSearch : AuthNavGraphRoute

    @Serializable
    data object MajorSearch : AuthNavGraphRoute
}
