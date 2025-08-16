package com.gongbaek.android.presentation.ui.groupregister.screen

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
import com.gongbaek.android.R
import com.gongbaek.android.presentation.type.SelectableButtonType
import com.gongbaek.android.presentation.ui.component.button.GongBaekBasicButton
import com.gongbaek.android.presentation.ui.component.button.GongBaekSelectableButtons
import com.gongbaek.android.presentation.ui.component.progressBar.GongBaekProgressBar
import com.gongbaek.android.presentation.ui.component.section.PageDescriptionSection
import com.gongbaek.android.presentation.ui.component.topbar.StartTitleTopBar
import com.gongbaek.android.ui.theme.GONGBAEKTheme

@Composable
fun SelectDayOfWeekRoute(
    viewModel: GroupRegisterViewModel,
    navigateGroupTime: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        viewModel.setEvent(GroupRegisterContract.Event.OnDayOfWeekDeleted)
        viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
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
            viewModel.setEvent(GroupRegisterContract.Event.OnDayOfWeekDeleted)
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
        }
    )
}

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
