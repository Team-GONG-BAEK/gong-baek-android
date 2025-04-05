package com.sopt.gongbaek.presentation.ui.auth.state

data class SelfIntroductionState(
    val selfIntroduction: String = "",
) {
    val isNextEnabled: Boolean
        get() = true
}
