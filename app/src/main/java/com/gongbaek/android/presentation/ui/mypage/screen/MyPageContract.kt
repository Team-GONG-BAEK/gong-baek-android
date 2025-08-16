package com.gongbaek.android.presentation.ui.mypage.screen

import com.gongbaek.android.BuildConfig
import com.gongbaek.android.domain.model.GroupInfo
import com.gongbaek.android.domain.model.UserInfo
import com.gongbaek.android.presentation.util.base.UiEvent
import com.gongbaek.android.presentation.util.base.UiLoadState
import com.gongbaek.android.presentation.util.base.UiSideEffect
import com.gongbaek.android.presentation.util.base.UiState

class MyPageContract {
    data class State(
        val myPageLoadState: UiLoadState = UiLoadState.Idle,
        val myPageInfo: UserInfo = UserInfo(),
        val registerGroupsLoadState: UiLoadState = UiLoadState.Idle,
        val registerActiveGroups: List<GroupInfo> = listOf(),
        val registerClosedGroups: List<GroupInfo> = listOf(),
        val applyGroupsLoadState: UiLoadState = UiLoadState.Idle,
        val applyActiveGroups: List<GroupInfo> = listOf(),
        val applyClosedGroups: List<GroupInfo> = listOf(),
        val versionName: String = BuildConfig.VERSION_NAME,
        val logoutDialogState: Boolean = false,
        val withdrawDialogState: Boolean = false
    ) : UiState

    sealed class Event : UiEvent {
        data object OnGetMyProfile : Event()
        data object OnRegisterGroupsTabClick : Event()
        data object OnApplyGroupsTabClick : Event()
        object OnLogoutClicked : Event()
        object OnWithdrawClicked : Event()
        data object OnLogoutDialogConfirmClicked : Event()
        data object OnLogoutDialogDismissClicked : Event()
        data object OnWithdrawDialogConfirmClicked : Event()
        data object OnWithdrawDialogDismissClicked : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data object NavigateSetting : SideEffect
        data class NavigateGroupDetail(val groupId: Int, val groupCycle: String) : SideEffect
        data class NavigateGroupRoom(val groupId: Int, val groupCycle: String) : SideEffect
        data object NavigateBack : SideEffect
        data object NavigateLogin : SideEffect
    }
}
