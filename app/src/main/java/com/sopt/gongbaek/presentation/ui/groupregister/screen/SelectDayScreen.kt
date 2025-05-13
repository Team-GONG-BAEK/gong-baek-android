package com.sopt.gongbaek.presentation.ui.groupregister.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.section.PageDescriptionSection
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.presentation.ui.groupregister.component.SelectDayCalendar
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import java.time.LocalDate

/**
 * Connects the group registration day selection UI with the ViewModel and handles navigation events.
 *
 * Observes UI state and side effects from the [GroupRegisterViewModel], manages back button behavior,
 * and coordinates navigation callbacks for progressing to the next step or returning to the previous screen.
 *
 * @param viewModel The ViewModel managing group registration state and side effects.
 * @param navigateGroupTime Callback invoked to navigate to the group time selection screen.
 * @param navigateBack Callback invoked to navigate back to the previous screen.
 */
@Composable
fun SelectDayRoute(
    viewModel: GroupRegisterViewModel,
    navigateGroupTime: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
        viewModel.setEvent(GroupRegisterContract.Event.OnWeekDateAndDayDeleted)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    GroupRegisterContract.SideEffect.NavigateBack -> navigateBack()
                    GroupRegisterContract.SideEffect.NavigateTime -> navigateGroupTime()
                    else -> {}
                }
            }
    }

    SelectDayScreen(
        selectedDate = uiState.groupRegisterInfo.weekDate.takeIf { it?.isNotBlank() == true }?.let {
            LocalDate.parse(it)
        },
        onSelectedDate = { selectedDate ->
            viewModel.setEvent(
                GroupRegisterContract.Event.OnWeekDateAndDaySelected(selectedDate, selectedDate.dayOfWeek.toString().take(3))
            )
        },
        onNextButtonClicked = {
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateTime)
        },
        onBackClick = {
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
            viewModel.setEvent(GroupRegisterContract.Event.OnWeekDateAndDayDeleted)
        }
    )
}

/**
 * Displays the day selection UI with a calendar and a "Next" button for group registration.
 *
 * Shows a calendar for selecting a date and a bottom-aligned button to proceed. The "Next" button is enabled only when a date is selected.
 *
 * @param selectedDate The currently selected date, or null if none is selected.
 * @param onSelectedDate Callback invoked when a date is selected from the calendar.
 * @param onNextButtonClicked Callback invoked when the "Next" button is clicked.
 * @param onBackClick Callback invoked when the back button is pressed.
 */
@Composable
private fun SelectDayScreen(
    selectedDate: LocalDate?,
    onSelectedDate: (LocalDate) -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SelectDaySection(
            selectedDate = selectedDate,
            onDateSelected = onSelectedDate,
            onBackClick = onBackClick
        )

        GongBaekBasicButton(
            title = stringResource(R.string.groupregister_next),
            onClick = onNextButtonClicked,
            enabled = selectedDate != null,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun SelectDaySection(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        StartTitleTopBar(
            onLeadingIconClick = onBackClick
        )
        GongBaekProgressBar(progressPercent = 0.125f * 2f)

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            PageDescriptionSection(
                titleResId = R.string.groupregister_selectday_title,
                modifier = Modifier.padding(top = 40.dp, bottom = 60.dp)
            )

            SelectDayCalendar(
                selectedDate = selectedDate,
                onDateSelected = onDateSelected
            )
        }
    }
}

/**
 * Preview composable for the Select Day screen with the app theme applied.
 *
 * This preview does not display any UI content.
 */
@Preview(showBackground = true)
@Composable
private fun ShowSelectDayScreen() {
    GONGBAEKTheme {
    }
}
