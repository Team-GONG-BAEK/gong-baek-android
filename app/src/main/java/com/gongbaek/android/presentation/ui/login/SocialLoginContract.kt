package com.gongbaek.android.presentation.ui.login

import android.content.Context
import com.gongbaek.android.presentation.util.base.UiEvent
import com.gongbaek.android.presentation.util.base.UiLoadState
import com.gongbaek.android.presentation.util.base.UiSideEffect
import com.gongbaek.android.presentation.util.base.UiState

class SocialLoginContract {

    data class State(
        val kakaoToken: String = "",
        val signInState: UiLoadState = UiLoadState.Idle
    ) : UiState

    sealed class Event : UiEvent {
        data class OnKaKaoLoginClick(val context: Context) : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data object NavigateTermsOfService : SideEffect
        data object NavigateHome : SideEffect
    }
}
