package com.gongbaek.android.presentation.ui.home.screen

import com.gongbaek.android.domain.model.NearestGroup
import com.gongbaek.android.domain.model.RecommendGroupInfo
import com.gongbaek.android.domain.model.UserLectureTimeTable
import com.gongbaek.android.domain.model.UserProfile
import com.gongbaek.android.presentation.util.base.UiEvent
import com.gongbaek.android.presentation.util.base.UiLoadState
import com.gongbaek.android.presentation.util.base.UiSideEffect
import com.gongbaek.android.presentation.util.base.UiState
import com.gongbaek.android.presentation.util.nearestGroupFormatSchedule

class HomeContract {

    data class State(
        val homeLoadState: UiLoadState = UiLoadState.Idle,
        val userProfile: UserProfile = UserProfile(),
        val nearestGroup: NearestGroup? = NearestGroup(),
        val nearestGroupSchedule: String = nearestGroupFormatSchedule(
            nearestGroup?.weekDate,
            nearestGroup?.startTime,
            nearestGroup?.endTime
        ),
        val onceRecommendGroupList: List<RecommendGroupInfo> = emptyList(),
        val weekRecommendGroupList: List<RecommendGroupInfo> = emptyList(),
        val userLectureTimeTable: List<UserLectureTimeTable> = emptyList(),
        val convertedUserLectureTimeTable: Map<String, List<Int>> = emptyMap()
    ) : UiState

    sealed class Event : UiEvent {
        data object OnFetchHomeData : Event()
    }

    sealed interface SideEffect : UiSideEffect {
        data class NavigateToGroupDetail(val groupId: Int, val groupType: String) : SideEffect
        data class NavigateToGroupRoom(val groupId: Int, val groupType: String) : SideEffect
        data object NavigateToGroupList : SideEffect
    }
}
