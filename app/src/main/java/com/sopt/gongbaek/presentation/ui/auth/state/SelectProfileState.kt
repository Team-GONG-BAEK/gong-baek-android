package com.sopt.gongbaek.presentation.ui.auth.state

data class SelectProfileState(
    val profileImageIndex: Int = DEFAULT_PROFILE_IMAGE_INDEX
) {
    val isNextEnabled: Boolean
        get() = profileImageIndex != DEFAULT_PROFILE_IMAGE_INDEX

    companion object {
        private const val DEFAULT_PROFILE_IMAGE_INDEX: Int = 0
    }
}
