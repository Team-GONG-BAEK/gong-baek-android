package com.gongbaek.android.presentation.ui.splash

import com.gongbaek.android.presentation.util.base.UiEvent
import com.gongbaek.android.presentation.util.base.UiSideEffect
import com.gongbaek.android.presentation.util.base.UiState

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
