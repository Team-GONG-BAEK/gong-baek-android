package com.gongbaek.android.presentation.ui.grouplist.screen

import com.gongbaek.android.domain.model.GroupInfo
import com.gongbaek.android.presentation.util.base.UiEvent
import com.gongbaek.android.presentation.util.base.UiLoadState
import com.gongbaek.android.presentation.util.base.UiSideEffect
import com.gongbaek.android.presentation.util.base.UiState

class GroupListContract {
    data class State(
        val loadState: UiLoadState = UiLoadState.Idle,
        val selectedDayOfWeekIndex: Int = 0,
        val selectedCategoryIndex: Int = 0,
        val toggleCheckedState: Boolean = true,
        val groups: List<GroupInfo> = listOf()
    ) : UiState

    sealed class Event : UiEvent {
        data class GetGroups(val category: String) : Event()
        data class OnDayOfWeekSelected(val index: Int) : Event()
        data class OnCategorySelected(val index: Int) : Event()
        data class OnToggleCheckStateChanged(val state: Boolean) : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data class NavigateGroupDetail(val groupId: Int, val groupCycle: String) : SideEffect
        data object NavigateGroupRegister : SideEffect
    }
}
