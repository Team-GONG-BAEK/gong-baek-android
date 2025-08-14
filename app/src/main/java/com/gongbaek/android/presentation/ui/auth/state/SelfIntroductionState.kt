package com.gongbaek.android.presentation.ui.auth.state

data class SelfIntroductionState(
    val selfIntroduction: String = ""
) {
    val isNextEnabled: Boolean
        get() = true
}
