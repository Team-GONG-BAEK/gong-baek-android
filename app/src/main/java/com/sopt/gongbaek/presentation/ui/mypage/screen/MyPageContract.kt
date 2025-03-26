package com.sopt.gongbaek.presentation.ui.mypage.screen

import com.sopt.gongbaek.domain.model.MyPageInfo
import com.sopt.gongbaek.presentation.util.base.UiEvent
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.presentation.util.base.UiSideEffect
import com.sopt.gongbaek.presentation.util.base.UiState

class MyPageContract {
    data class State(
        val MyPageLoadState: UiLoadState = UiLoadState.Idle,
        val myPageInfo: MyPageInfo = MyPageInfo()
    ) : UiState

    sealed class Event : UiEvent {
        data object OnRegisterGroupsTabClick : Event()
        data object OnApplyGroupsTabClick : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data class NavigateGroupDetail(val groupId: Int, val groupCycle: String) : SideEffect
        data class NavigateGroupRoom(val groupId: Int, val groupCycle: String) : SideEffect
    }
}