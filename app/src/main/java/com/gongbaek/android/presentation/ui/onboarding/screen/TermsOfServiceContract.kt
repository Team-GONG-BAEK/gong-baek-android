package com.gongbaek.android.presentation.ui.onboarding.screen

import com.gongbaek.android.presentation.util.base.UiEvent
import com.gongbaek.android.presentation.util.base.UiSideEffect
import com.gongbaek.android.presentation.util.base.UiState

class TermsOfServiceContract {

    data class State(
        val termsOfService: Boolean = false,
        val privacyPolicy: Boolean = false
    ) : UiState {
        val fullAcceptance: Boolean
            get() = termsOfService && privacyPolicy
    }

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
