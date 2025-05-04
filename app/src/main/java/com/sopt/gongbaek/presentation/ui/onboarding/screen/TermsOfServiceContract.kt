package com.sopt.gongbaek.presentation.ui.onboarding.screen

import com.sopt.gongbaek.presentation.util.base.UiEvent
import com.sopt.gongbaek.presentation.util.base.UiSideEffect
import com.sopt.gongbaek.presentation.util.base.UiState

class TermsOfServiceContract {

    data class State(
        val termsOfService: Boolean = false,
        val privacyPolicy: Boolean = false,
        val fullAcceptance: Boolean = false
    ) : UiState

    sealed class Event : UiEvent {
        data object OnFullAcceptClick : Event()
        data object OnTermsOfServiceClick : Event()
        data object OnPrivacyPolicyClick : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data object OnBackClick : SideEffect
        data object OnTermsOfServiceDetailClick : SideEffect
        data object OnPrivacyPolicyDetailClick : SideEffect
        data object OnNextClick : SideEffect
    }
}
