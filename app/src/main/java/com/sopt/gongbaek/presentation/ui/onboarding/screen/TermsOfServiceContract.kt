package com.sopt.gongbaek.presentation.ui.onboarding.screen

import com.sopt.gongbaek.presentation.util.base.UiEvent
import com.sopt.gongbaek.presentation.util.base.UiSideEffect
import com.sopt.gongbaek.presentation.util.base.UiState

class TermsOfServiceContract {

    data class State(
        val termsOfService: Boolean = false,
        val privacyPolicy: Boolean = false,
        val marketingPolicy: Boolean = false,
        val fullAcceptance: Boolean = termsOfService && privacyPolicy && marketingPolicy
    ) : UiState

    sealed class Event : UiEvent {
        data object OnFullAcceptClick : Event()
        data object OnTermsOfServiceClick : Event()
        data object OnPrivacyPolicyClick : Event()
        data object OnMarketingPolicyClick : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data object OnBackClick : SideEffect
        data object OnTermsOfServiceDetailClick : SideEffect
        data object OnPrivacyPolicyDetailClick : SideEffect
        data object OnMarketingPolicyDetailClick : SideEffect
        data object OnNextClick : SideEffect
    }
}
