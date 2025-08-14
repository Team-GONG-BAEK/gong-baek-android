package com.gongbaek.android.presentation.ui.auth.state

data class MbtiState(
    val firstLetter: String = "", // E or I
    val secondLetter: String = "", // N or S
    val thirdLetter: String = "", // F or T
    val forthLetter: String = "" // P or J
) {
    val isNextEnabled: Boolean
        get() = firstLetter.isNotEmpty() && secondLetter.isNotEmpty() && thirdLetter.isNotEmpty() && forthLetter.isNotEmpty()
}
