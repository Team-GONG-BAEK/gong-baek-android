package com.sopt.gongbaek.presentation.ui.groupregister.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.domain.type.GroupCycleType
import com.sopt.gongbaek.presentation.type.SelectableButtonType
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekSelectableButtons
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.section.PageDescriptionSection
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

/**
 * Connects the group cycle selection UI with the view model and handles navigation events.
 *
 * Observes the group registration state and side effects from the [GroupRegisterViewModel], manages back button behavior,
 * and triggers navigation callbacks based on user actions and side effects.
 *
 * @param navigateDay Called to navigate to the day selection screen.
 * @param navigateDayOfWeek Called to navigate to the day-of-week selection screen.
 * @param navigateBack Called to navigate back in the registration flow.
 */
@Composable
fun GroupCycleRoute(
    viewModel: GroupRegisterViewModel,
    navigateDay: () -> Unit,
    navigateDayOfWeek: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
        viewModel.setEvent(GroupRegisterContract.Event.OnGroupCycleDeleted)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    GroupRegisterContract.SideEffect.NavigateBack -> navigateBack()
                    GroupRegisterContract.SideEffect.NavigateDay -> navigateDay()
                    GroupRegisterContract.SideEffect.NavigateDayOfWeek -> navigateDayOfWeek()
                    else -> {}
                }
            }
    }

    GroupCycleScreen(
        groupCycle = uiState.groupRegisterInfo.groupType,
        selectedGroupCycle = uiState.selectedGroupType,
        onGroupCycleSelected = { groupCycle ->
            viewModel.setEvent(
                GroupRegisterContract.Event.OnGroupCycleSelected(groupCycle)
            )
        },
        onNextButtonClicked = {
            when (uiState.groupRegisterInfo.groupType) {
                GroupCycleType.ONCE.name -> viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateDay)
                GroupCycleType.WEEKLY.name -> viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateDayOfWeek)
            }
        },
        onBackClick = {
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
            viewModel.setEvent(GroupRegisterContract.Event.OnGroupCycleDeleted)
        }
    )
}

/**
 * Displays the group cycle selection screen with selectable options and a next button.
 *
 * Presents the group cycle selection UI and a bottom-aligned next button, which is enabled only when a group cycle is selected.
 *
 * @param groupCycle The currently selected group cycle as a string.
 * @param selectedGroupCycle The group cycle option that is currently highlighted.
 * @param onGroupCycleSelected Callback invoked when a group cycle option is selected.
 * @param onNextButtonClicked Callback invoked when the next button is clicked.
 * @param onBackClick Callback invoked when the back button is pressed.
 */
@Composable
private fun GroupCycleScreen(
    groupCycle: String,
    selectedGroupCycle: String,
    onGroupCycleSelected: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GroupCycleSection(
            onBackClick = onBackClick,
            selectedOption = selectedGroupCycle,
            onOptionSelected = onGroupCycleSelected
        )

        GongBaekBasicButton(
            title = stringResource(R.string.groupregister_next),
            onClick = onNextButtonClicked,
            enabled = groupCycle.isNotBlank(),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun GroupCycleSection(
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    selectedOption: String? = null,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        StartTitleTopBar(onLeadingIconClick = onBackClick)
        GongBaekProgressBar(0.125f)

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            PageDescriptionSection(
                titleResId = R.string.groupregister_cycle_title,
                modifier = Modifier.padding(top = 40.dp, bottom = 20.dp)
            )

            GongBaekSelectableButtons(
                selectableButtonType = SelectableButtonType.GROUP_CYCLE,
                onOptionSelected = onOptionSelected,
                selectedOption = selectedOption
            )
            Spacer(Modifier.height(48.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_mark_16),
                    contentDescription = null,
                    tint = GongBaekTheme.colors.mainOrange
                )
                Spacer(Modifier.width(6.dp))

                Text(
                    text = stringResource(R.string.groupregister_cycle_description),
                    color = GongBaekTheme.colors.mainOrange,
                    style = GongBaekTheme.typography.caption1.m13
                )
            }
        }
    }
}

/**
 * Displays a preview of the GroupCycleScreen composable with default parameters for UI development.
 */
@Preview(showBackground = true)
@Composable
private fun ShowGroupCycleScreen() {
    GONGBAEKTheme {
        GroupCycleScreen(
            groupCycle = "",
            selectedGroupCycle = "",
            onGroupCycleSelected = {},
            onNextButtonClicked = {},
            onBackClick = {}
        )
    }
}
