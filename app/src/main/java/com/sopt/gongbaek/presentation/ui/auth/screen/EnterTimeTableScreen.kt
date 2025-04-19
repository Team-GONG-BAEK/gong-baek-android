package com.sopt.gongbaek.presentation.ui.auth.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.sopt.gongbaek.presentation.ui.auth.state.EnterTimeTableState
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.section.PageDescriptionSection
import com.sopt.gongbaek.presentation.ui.component.timetable.LectureTimeSelectionTable
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun EnterTimeTableRoute(
    viewModel: AuthViewModel,
    navigateCompleteAuth: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is AuthContract.SideEffect.NavigateCompleteAuth -> navigateCompleteAuth()
                    is AuthContract.SideEffect.NavigateBack -> navigateBack()
                    else -> {}
                }
            }
    }

    EnterTimeTableScreen(
        uiState = uiState.enterTimeTableState,
        onTimeSlotSelectionChanged = { day, timeSlots ->
            viewModel.setEvent(AuthContract.Event.TimeSlotSelectionChanged(day, timeSlots))
        },
        onNextClick = { viewModel.setEvent(AuthContract.Event.RequestSingUp) },
        onBackClick = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack) }
    )
}

@Composable
private fun EnterTimeTableScreen(
    uiState: EnterTimeTableState,
    onTimeSlotSelectionChanged: (String, List<Int>) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            GongBaekBasicButton(
                title = stringResource(R.string.auth_enter_timetable_complete_button),
                enabled = uiState.isNextEnabled,
                onClick = onNextClick,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        },
        containerColor = GongBaekTheme.colors.white,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        content = { paddingValues ->
            EnterTimeTableSection(
                onBackClick = onBackClick,
                selectedTimeSlotsByDay = uiState.selectedTimeSlotsByDay,
                onTimeSlotSelectionChange = onTimeSlotSelectionChanged,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            )
        }
    )
}

@Composable
private fun EnterTimeTableSection(
    onBackClick: () -> Unit,
    selectedTimeSlotsByDay: Map<String, List<Int>>,
    onTimeSlotSelectionChange: (String, List<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        StartTitleTopBar(onLeadingIconClick = onBackClick)

        GongBaekProgressBar(progressPercent = 1f)

        Column(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.height(54.dp))

            PageDescriptionSection(
                titleResId = R.string.auth_enter_timetable_title,
                descriptionResId = R.string.auth_enter_timetable_description
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {
                item {
                    val timeSlotLabels = listOf("9", "10", "11", "12", "13", "14", "15", "16", "17")

                    LectureTimeSelectionTable(
                        timeSlotLabels = timeSlotLabels,
                        selectedTimeSlotsByDay = selectedTimeSlotsByDay,
                        onTimeSlotSelectionChange = onTimeSlotSelectionChange
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EnterTimeTableScreenPreview() {
    GONGBAEKTheme {
        EnterTimeTableScreen(
            uiState = EnterTimeTableState(),
            onTimeSlotSelectionChanged = { _, _ -> },
            onNextClick = {},
            onBackClick = {}
        )
    }
}
