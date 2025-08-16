package com.gongbaek.android.presentation.ui.onboarding.screen

import com.gongbaek.android.presentation.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsOfServiceViewModel @Inject constructor() : BaseViewModel<TermsOfServiceContract.State, TermsOfServiceContract.Event, TermsOfServiceContract.SideEffect>() {

    override fun createInitialState() = TermsOfServiceContract.State()

    override suspend fun handleEvent(event: TermsOfServiceContract.Event) {
        when (event) {
            is TermsOfServiceContract.Event.OnFullAcceptClick -> {
                val newState = !currentState.fullAcceptance
                setState {
                    copy(
                        termsOfService = newState,
                        privacyPolicy = newState
                    )
                }
            }

            is TermsOfServiceContract.Event.OnTermsOfServiceClick -> {
                setState { copy(termsOfService = !currentState.termsOfService) }
            }

            is TermsOfServiceContract.Event.OnPrivacyPolicyClick -> {
                setState { copy(privacyPolicy = !currentState.privacyPolicy) }
            }
        }
    }

    fun sendSideEffect(sideEffect: TermsOfServiceContract.SideEffect) = setSideEffect(sideEffect)
}
