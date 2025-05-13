package com.sopt.gongbaek.presentation.ui.groupregister.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.domain.type.DayOfWeekType
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.section.PageDescriptionSection
import com.sopt.gongbaek.presentation.ui.component.timetable.GroupRegisterTimeTable
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

/**
 * Composable route for the group time registration screen, connecting UI state and navigation with the GroupRegisterViewModel.
 *
 * Observes state and side effects from the view model, handles back navigation, and triggers data loading and navigation events.
 *
 * @param viewModel The view model managing group registration state and events.
 * @param navigateGroupCategory Callback to navigate to the group category selection screen.
 * @param navigateBack Callback to navigate back in the navigation stack.
 */
@Composable
fun GroupTimeRoute(
    viewModel: GroupRegisterViewModel,
    navigateGroupCategory: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
        viewModel.setEvent(GroupRegisterContract.Event.OnTimeSlotDeleted)
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(GroupRegisterContract.Event.GetLectureTime)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    GroupRegisterContract.SideEffect.NavigateBack -> navigateBack()
                    GroupRegisterContract.SideEffect.NavigateCategory -> navigateGroupCategory()
                    else -> {}
                }
            }
    }
    val selectedDay = DayOfWeekType.toDayOfWeekRemoveSuffix(uiState.groupRegisterInfo.weekDay)

    GroupTimeScreen(
        selectedDay = selectedDay,
        lectureTime = uiState.lectureTime,
        selectedTimeSlotsByDay = uiState.selectedTimeSlotsByDay,
        onTimeSlotSelectionChange = { day, updatedIndices ->
            viewModel.setEvent(GroupRegisterContract.Event.OnTimeSlotSelected(day, updatedIndices))
        },
        onNextButtonClicked = {
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateCategory)
        },
        onBackClick = {
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
            viewModel.setEvent(GroupRegisterContract.Event.OnTimeSlotDeleted)
        }
    )
}

/**
 * Displays the group time selection screen, allowing users to choose available time slots for a specific day.
 *
 * Presents a timetable for the selected day, highlights selected time slots, and provides navigation controls.
 * The "Next" button is enabled only when at least one time slot is selected.
 *
 * @param selectedDay The day for which time slots are being selected.
 * @param lectureTime A map of days to available lecture time slots.
 * @param selectedTimeSlotsByDay A map of days to the currently selected time slots.
 * @param onTimeSlotSelectionChange Callback invoked when the selection of time slots changes for a day.
 * @param onNextButtonClicked Callback invoked when the "Next" button is clicked.
 * @param onBackClick Callback invoked when the back button is pressed.
 * @param timeSlotLabels Labels for the time slots displayed in the timetable.
 */
@Composable
private fun GroupTimeScreen(
    selectedDay: String,
    lectureTime: Map<String, List<Int>>,
    selectedTimeSlotsByDay: Map<String, List<Int>>,
    onTimeSlotSelectionChange: (String, List<Int>) -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackClick: () -> Unit,
    timeSlotLabels: List<String> = listOf("9", "10", "11", "12", "13", "14", "15", "16", "17")
) {
    Scaffold(
        bottomBar = {
            GongBaekBasicButton(
                title = stringResource(R.string.groupregister_next),
                onClick = onNextButtonClicked,
                enabled = selectedTimeSlotsByDay.values.any { it.isNotEmpty() },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
        containerColor = GongBaekTheme.colors.white
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            StartTitleTopBar(
                onLeadingIconClick = onBackClick
            )
            GongBaekProgressBar(progressPercent = 0.125f * 3f)

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                PageDescriptionSection(
                    titleResId = R.string.groupregister_time_title,
                    modifier = Modifier.padding(top = 40.dp, bottom = 20.dp)
                )

                GroupRegisterTimeTable(
                    timeSlotLabels = timeSlotLabels,
                    selectedDay = selectedDay,
                    selectedTimeSlotsByDay = selectedTimeSlotsByDay,
                    lectureTime = lectureTime,
                    onTimeSlotSelectionChange = onTimeSlotSelectionChange
                )
            }
        }
    }
}

/**
 * Preview of the group time selection screen with the app theme applied.
 *
 * Displays the themed scaffold without rendering any UI content.
 */
@Preview(showBackground = true)
@Composable
private fun ShowGroupTimeScreen() {
    GONGBAEKTheme {
    }
}
