package com.sopt.gongbaek.presentation.ui.auth.state

data class EnterTimeTableState(
    val selectedTimeSlotsByDay: Map<String, List<Int>> = emptyMap()
) {
    val isNextEnabled: Boolean
        get() = true
}
