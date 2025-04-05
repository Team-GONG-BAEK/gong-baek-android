package com.sopt.gongbaek.presentation.ui.auth.state

data class MbtiState(
    val energy: String = "",       // E or I
    val perception: String = "",   // N or S
    val decision: String = "",     // F or T
    val lifestyle: String = ""     // P or J
) {
    val isNextEnabled: Boolean
        get() = energy.isNotEmpty() && perception.isNotEmpty() && decision.isNotEmpty() && lifestyle.isNotEmpty()
}
