package com.sopt.gongbaek.presentation.ui.auth.state

data class NicknameGenderState(
    val nickname: String = "",
    val nicknameErrorMessage: String? = null,
    val gender: String = "",
) {
    val isNextEnabled: Boolean
        get() = nickname.isNotEmpty() && gender.isNotEmpty()
}
