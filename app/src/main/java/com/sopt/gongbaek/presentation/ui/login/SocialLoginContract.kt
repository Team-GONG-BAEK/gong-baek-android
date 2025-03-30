package com.sopt.gongbaek.presentation.ui.login

import android.content.Context
import com.sopt.gongbaek.presentation.util.base.UiEvent
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.presentation.util.base.UiSideEffect
import com.sopt.gongbaek.presentation.util.base.UiState

class SocialLoginContract {

    data class State(
        val kakaoToken: String? = null,
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
