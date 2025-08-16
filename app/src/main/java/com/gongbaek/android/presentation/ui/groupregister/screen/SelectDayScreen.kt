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
import com.gongbaek.android.presentation.ui.component.button.GongBaekBasicButton
import com.gongbaek.android.presentation.ui.component.progressBar.GongBaekProgressBar
import com.gongbaek.android.presentation.ui.component.section.PageDescriptionSection
import com.gongbaek.android.presentation.ui.component.topbar.StartTitleTopBar
import com.gongbaek.android.presentation.ui.groupregister.component.SelectDayCalendar
import com.gongbaek.android.ui.theme.GONGBAEKTheme
import java.time.LocalDate

@Composable
fun SelectDayRoute(
    viewModel: GroupRegisterViewModel,
    navigateGroupTime: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        viewModel.setEvent(GroupRegisterContract.Event.OnWeekDateAndDayDeleted)
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
            viewModel.setEvent(GroupRegisterContract.Event.OnWeekDateAndDayDeleted)
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
        }
    )
}

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

@Preview(showBackground = true)
@Composable
private fun ShowSelectDayScreen() {
    GONGBAEKTheme {
    }
}
