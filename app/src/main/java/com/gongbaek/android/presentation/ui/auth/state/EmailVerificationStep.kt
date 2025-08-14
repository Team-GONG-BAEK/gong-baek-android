package com.gongbaek.android.presentation.ui.auth.state

enum class EmailVerificationStep {
    INITIAL,
    REQUEST_FAILED,
    REQUESTED,
    VERIFICATION_FAILED,
    VERIFIED
}
