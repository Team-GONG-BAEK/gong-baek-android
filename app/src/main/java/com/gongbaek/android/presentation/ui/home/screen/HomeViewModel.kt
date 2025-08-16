package com.gongbaek.android.presentation.ui.home.screen

import androidx.lifecycle.viewModelScope
import com.gongbaek.android.domain.usecase.FetchHomeScreenUseCase
import com.gongbaek.android.domain.usecase.FetchLatestGroupUseCase
import com.gongbaek.android.domain.usecase.FetchUserLectureTimetableUseCase
import com.gongbaek.android.domain.usecase.FetchUserProfileUseCase
import com.gongbaek.android.domain.usecase.SetLectureTimetableUseCase
import com.gongbaek.android.presentation.util.base.BaseViewModel
import com.gongbaek.android.presentation.util.base.UiLoadState
import com.gongbaek.android.presentation.util.timetable.convertToSlotsByDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchHomeScreenUseCase: FetchHomeScreenUseCase,
    private val fetchLatestGroupUseCase: FetchLatestGroupUseCase,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val fetchUserLectureTimetableUseCase: FetchUserLectureTimetableUseCase,
    private val setLectureTimetableUseCase: SetLectureTimetableUseCase
) : BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.SideEffect>() {

    init {
        fetchAllHomeData()
    }

    override fun createInitialState(): HomeContract.State = HomeContract.State()

    override suspend fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnFetchHomeData -> fetchAllHomeData()
        }
    }

    fun sendSideEffect(sideEffect: HomeContract.SideEffect) = setSideEffect(sideEffect)

    private fun fetchAllHomeData() = viewModelScope.launch {
        setState { copy(homeLoadState = UiLoadState.Loading) }

        val homeInfoDeferred = async { fetchHomeScreenUseCase() }
        delay(100)
        val onceGroupDeferred = async { fetchLatestGroupUseCase("ONCE") }
        delay(100)
        val weekGroupDeferred = async { fetchLatestGroupUseCase("WEEKLY") }
        delay(100)
        val profileDeferred = async { fetchUserProfileUseCase() }
        delay(100)
        val timetableDeferred = async { fetchUserLectureTimetableUseCase() }

        val homeInfo = homeInfoDeferred.await()
        val onceGroup = onceGroupDeferred.await()
        val weekGroup = weekGroupDeferred.await()
        val profile = profileDeferred.await()
        val timetable = timetableDeferred.await()

        val convertedTimetable = convertToSlotsByDay(timetable.getOrThrow())

        setState {
            copy(
                homeLoadState = UiLoadState.Success,
                nearestGroup = homeInfo.getOrThrow(),
                onceRecommendGroupList = onceGroup.getOrThrow(),
                weekRecommendGroupList = weekGroup.getOrThrow(),
                userProfile = profile.getOrThrow(),
                userLectureTimeTable = timetable.getOrThrow(),
                convertedUserLectureTimeTable = convertedTimetable
            )
        }

        setUserLectureTimetable(convertedTimetable)
    }

    private fun setUserLectureTimetable(lectureTimetable: Map<String, List<Int>>) =
        viewModelScope.launch {
            setLectureTimetableUseCase(lectureTimetable)
        }
}
