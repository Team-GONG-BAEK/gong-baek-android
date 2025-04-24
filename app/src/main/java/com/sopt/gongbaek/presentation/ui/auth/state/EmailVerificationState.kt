package com.sopt.gongbaek.presentation.ui.auth.state

data class EmailVerificationState(
    val email: String = "",
    val verificationCode: String = "",
    val step: EmailVerificationStep = EmailVerificationStep.INITIAL,
    val emailMessage: String = "",
    val verificationCodeMessage: String = "",
    val isVerificationCodeEnabled: Boolean = false,
    val isTimerRunning: Boolean = false,
    val timeLeft: Int = 180
) {
    val isNextEnabled: Boolean
        get() = step == EmailVerificationStep.VERIFIED
}
