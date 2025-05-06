package com.sopt.gongbaek.presentation.ui.splash

import com.sopt.gongbaek.presentation.util.base.UiEvent
import com.sopt.gongbaek.presentation.util.base.UiSideEffect
import com.sopt.gongbaek.presentation.util.base.UiState

class SplashContract {

    data class State(
        val autoLogin: Boolean? = null
    ) : UiState

    sealed class Event : UiEvent {
        data object ValidateAutoLogin : Event()
        data object ResetAutoLogin : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data object NavigateSocialLogin : SideEffect
        data object NavigateHome : SideEffect
    }
}
