package com.sopt.gongbaek.presentation.ui.auth.state

data class SelectProfileState(
    val profileImageIndex: Int? = null
) {
    val isNextEnabled: Boolean
        get() = profileImageIndex != null
}
