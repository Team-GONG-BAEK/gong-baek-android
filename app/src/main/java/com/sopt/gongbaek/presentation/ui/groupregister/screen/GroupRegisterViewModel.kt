package com.sopt.gongbaek.presentation.ui.groupregister.screen

import androidx.lifecycle.viewModelScope
import com.sopt.gongbaek.domain.model.GroupRegisterInfo
import com.sopt.gongbaek.domain.type.DayOfWeekType
import com.sopt.gongbaek.domain.usecase.GetLectureTimetableUseCase
import com.sopt.gongbaek.domain.usecase.PostGroupUseCase
import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import com.sopt.gongbaek.presentation.util.timetable.convertToTimeTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRegisterViewModel @Inject constructor(
    private val postGroupUseCase: PostGroupUseCase,
    private val getLectureTimetableUseCase: GetLectureTimetableUseCase
) : BaseViewModel<GroupRegisterContract.State, GroupRegisterContract.Event, GroupRegisterContract.SideEffect>() {

    override fun createInitialState(): GroupRegisterContract.State = GroupRegisterContract.State(
        registerState = UiLoadState.Idle
    )

    /**
     * Handles UI events related to group registration and updates the state or triggers actions accordingly.
     *
     * Processes user interactions such as selecting group cycle, day, category, cover image, place, people count, title, introduction, and time slots. Initiates group registration, fetches lecture timetables, manages dialog actions, and updates or resets relevant state fields based on the received event.
     *
     * @param event The event representing a user action or UI interaction in the group registration flow.
     */
    override suspend fun handleEvent(event: GroupRegisterContract.Event) {
        when (event) {
            is GroupRegisterContract.Event.GetLectureTime -> getLectureTime()

            is GroupRegisterContract.Event.OnGroupCycleSelected -> {
                updateGroupRegisterInfo { copy(groupType = setGroupType(event.groupType)) }
                setState { copy(selectedGroupType = event.groupType) }
            }

            is GroupRegisterContract.Event.OnDayOfWeekSelected -> {
                updateGroupRegisterInfo { copy(weekDay = setWeekDay(event.weekDay)) }
                setState { copy(selectedDayOfWeek = event.weekDay) }
            }

            is GroupRegisterContract.Event.OnWeekDateAndDaySelected -> {
                updateGroupRegisterInfo {
                    copy(
                        weekDate = event.weekDate.toString(),
                        weekDay = event.weekDay
                    )
                }
            }

            is GroupRegisterContract.Event.OnCategorySelected -> {
                updateGroupRegisterInfo { copy(category = setCategory(event.category)) }
                setState { copy(selectedCategory = event.category) }
            }

            is GroupRegisterContract.Event.OnCoverSelected -> {
                updateGroupRegisterInfo { copy(coverImg = event.cover) }
                setState { copy(selectedCover = event.cover) }
            }

            is GroupRegisterContract.Event.OnPlaceChanged -> {
                updateGroupRegisterInfo { copy(location = event.place) }
            }

            is GroupRegisterContract.Event.OnPeopleChanged -> {
                updateGroupRegisterInfo { copy(maxPeopleCount = event.peopleCount) }
            }

            is GroupRegisterContract.Event.OnTitleChanged -> {
                updateGroupRegisterInfo { copy(groupTitle = event.title) }
            }

            is GroupRegisterContract.Event.OnIntroductionChanged -> {
                updateGroupRegisterInfo { copy(introduction = event.introduction) }
            }

            is GroupRegisterContract.Event.OnRegisterButtonClicked -> {
                registerGroup(groupRegisterInfo = event.groupRegisterInfo)
            }

            is GroupRegisterContract.Event.OnDialogConfirmClicked -> {
                isRegistering = false
                setSideEffect(GroupRegisterContract.SideEffect.NavigateMyGroup)
            }

            is GroupRegisterContract.Event.OnDialogDismissClicked -> {
                setState { copy(registerState = UiLoadState.Idle) }
            }

            is GroupRegisterContract.Event.OnTimeSlotSelected -> {
                val updatedTimeSlots = currentState.selectedTimeSlotsByDay.toMutableMap().apply {
                    this[event.day] = event.timeSlots
                }
                setState { copy(selectedTimeSlotsByDay = updatedTimeSlots) }
                updateGroupRegisterTime(updatedTimeSlots)
            }

            is GroupRegisterContract.Event.OnTimeSlotDeleted -> {
                updateGroupRegisterInfo { copy(startTime = 0.0, endTime = 0.0) }
                setState { copy(selectedTimeSlotsByDay = emptyMap()) }
            }

            is GroupRegisterContract.Event.OnGroupCycleDeleted -> {
                updateGroupRegisterInfo { copy(groupType = "") }
                setState { copy(selectedGroupType = "") }
            }

            is GroupRegisterContract.Event.OnWeekDateAndDayDeleted -> {
                updateGroupRegisterInfo { copy(weekDay = "", weekDate = "") }
                setState { copy(selectedDayOfWeek = "") }
            }

            is GroupRegisterContract.Event.OnDayOfWeekDeleted -> {
                updateGroupRegisterInfo { copy(weekDay = "") }
                setState { copy(selectedDayOfWeek = "") }
            }

            is GroupRegisterContract.Event.OnCategoryDeleted -> {
                updateGroupRegisterInfo { copy(category = "") }
                setState { copy(selectedCategory = "") }
            }

            is GroupRegisterContract.Event.OnCoverDeleted -> {
                updateGroupRegisterInfo { copy(coverImg = 0) }
                setState { copy(selectedCover = null) }
            }

            is GroupRegisterContract.Event.OnPlacePeopleDeleted -> {
                updateGroupRegisterInfo { copy(location = "", maxPeopleCount = 2) }
            }

            is GroupRegisterContract.Event.OnTitleIntroductionDeleted -> {
                updateGroupRegisterInfo { copy(groupTitle = "", introduction = "") }
            }
        }
    }

    /**
     * Updates the group's start and end times based on the provided time slot selections.
     *
     * Converts the given map of time slots into a timetable and sets the group's start and end times
     * to those of the first timetable entry if available, or resets them to zero if no time slots are selected.
     *
     * @param timeSlots A map where keys represent days and values are lists of selected time indices.
     */
    private fun updateGroupRegisterTime(timeSlots: Map<String, List<Int>>) {
        val lectureTimeTable = convertToTimeTable(timeSlots)
        if (lectureTimeTable.isNotEmpty()) {
            val startTime = lectureTimeTable.first().startTime
            val endTime = lectureTimeTable.first().endTime

            updateGroupRegisterInfo {
                copy(startTime = startTime, endTime = endTime)
            }
        } else {
            updateGroupRegisterInfo {
                copy(startTime = 0.0, endTime = 0.0)
            }
        }
    }

    private var isRegistering = false
    private fun registerGroup(groupRegisterInfo: GroupRegisterInfo) {
        if (isRegistering) return
        isRegistering = true

        viewModelScope.launch {
            setState { copy(registerState = UiLoadState.Loading) }

            runCatching {
                postGroupUseCase(groupRegisterInfo = groupRegisterInfo).fold(
                    onSuccess = {
                        setState { copy(registerState = UiLoadState.Success) }
                    },
                    onFailure = { error ->
                        isRegistering = false
                        setState { copy(registerState = UiLoadState.Error) }
                    }
                )
            }
        }
    }

    private fun getLectureTime() {
        viewModelScope.launch {
            setState { copy(loadState = UiLoadState.Loading) }

            runCatching {
                getLectureTimetableUseCase()
            }.onSuccess { timeTable ->
                val transformedTimeTable = timeTable.mapKeys { dayofweek ->
                    DayOfWeekType.toDayOfWeekRemoveSuffix(dayofweek.key)
                }

                setState {
                    copy(
                        lectureTime = transformedTimeTable,
                        loadState = UiLoadState.Success
                    )
                }
            }.onFailure {
                setState { copy(loadState = UiLoadState.Error) }
            }
        }
    }

    fun sendSideEffect(sideEffect: GroupRegisterContract.SideEffect) = setSideEffect(sideEffect)

    private fun updateGroupRegisterInfo(update: GroupRegisterInfo.() -> GroupRegisterInfo) =
        setState { copy(groupRegisterInfo = groupRegisterInfo.update()) }
}
