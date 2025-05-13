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
import com.sopt.gongbaek.presentation.type.SelectableButtonType
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekSelectableButtons
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.section.PageDescriptionSection
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme

/**
 * Connects the day-of-week selection UI with the group registration ViewModel and handles navigation events.
 *
 * Observes UI state and side effects from the ViewModel, manages back button behavior, and triggers navigation callbacks based on user actions or side effects.
 *
 * @param navigateGroupTime Callback invoked to navigate to the group time selection screen.
 * @param navigateBack Callback invoked to navigate back in the registration flow.
 */
@Composable
fun SelectDayOfWeekRoute(
    viewModel: GroupRegisterViewModel,
    navigateGroupTime: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
        viewModel.setEvent(GroupRegisterContract.Event.OnDayOfWeekDeleted)
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

    SelectDayOfWeekScreen(
        dayOfWeek = uiState.groupRegisterInfo.weekDay,
        selectedDayOfWeek = uiState.selectedDayOfWeek,
        onDayOfWeekSelected = { dayOfWeek ->
            viewModel.setEvent(
                GroupRegisterContract.Event.OnDayOfWeekSelected(dayOfWeek)
            )
        },
        onNextButtonClicked = {
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateTime)
        },
        onBackClick = {
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
            viewModel.setEvent(GroupRegisterContract.Event.OnDayOfWeekDeleted)
        }
    )
}

/**
 * Displays the UI for selecting a day of the week with a bottom-aligned "Next" button.
 *
 * Renders the day selection section and enables the "Next" button only when a day is selected.
 *
 * @param dayOfWeek The currently selected day of the week as a string.
 * @param selectedDayOfWeek The day of the week option currently highlighted in the UI.
 * @param onDayOfWeekSelected Callback invoked when a day is selected.
 * @param onNextButtonClicked Callback invoked when the "Next" button is clicked.
 * @param onBackClick Callback invoked when the back button is pressed.
 */
@Composable
private fun SelectDayOfWeekScreen(
    dayOfWeek: String,
    selectedDayOfWeek: String,
    onDayOfWeekSelected: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SelectDayOfWeekSection(
            onBackClick = onBackClick,
            selectedOption = selectedDayOfWeek,
            onOptionSelected = onDayOfWeekSelected
        )

        GongBaekBasicButton(
            title = stringResource(R.string.groupregister_next),
            onClick = onNextButtonClicked,
            enabled = dayOfWeek.isNotBlank(),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.BottomCenter)

        )
    }
}

@Composable
private fun SelectDayOfWeekSection(
    onBackClick: () -> Unit,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    selectedOption: String? = null
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
                titleResId = R.string.groupregister_selectdayofweek_title,
                modifier = Modifier.padding(top = 40.dp, bottom = 60.dp)
            )

            GongBaekSelectableButtons(
                selectableButtonType = SelectableButtonType.DAY_OF_WEEK,
                onOptionSelected = onOptionSelected,
                selectedOption = selectedOption
            )
        }
    }
}

/**
 * Displays a preview of the SelectDayOfWeekScreen composable with default parameters for UI development.
 */
@Preview(showBackground = true)
@Composable
private fun ShowSelectDayOfWeekScreen() {
    GONGBAEKTheme {
        SelectDayOfWeekScreen(
            dayOfWeek = "",
            selectedDayOfWeek = "",
            onDayOfWeekSelected = {},
            onNextButtonClicked = {},
            onBackClick = {}
        )
    }
}
