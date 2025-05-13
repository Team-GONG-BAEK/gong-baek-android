package com.sopt.gongbaek.presentation.ui.groupregister.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.type.GongBaekBasicTextFieldType
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.section.PageDescriptionSection
import com.sopt.gongbaek.presentation.ui.component.textfield.GongBaekBasicTextField
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme

/**
 * Connects the group introduction registration UI with the ViewModel, handling state, navigation, and back button behavior.
 *
 * Observes the group title and introduction state, manages navigation side effects, and responds to user actions such as back navigation and progressing to the next registration step.
 *
 * @param viewModel The ViewModel managing group registration state and events.
 * @param navigateRegister Callback invoked to navigate to the next registration step.
 * @param navigateBack Callback invoked to navigate back in the registration flow.
 */
@Composable
fun GroupIntroductionRoute(
    viewModel: GroupRegisterViewModel,
    navigateRegister: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
        viewModel.setEvent(GroupRegisterContract.Event.OnTitleIntroductionDeleted)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    GroupRegisterContract.SideEffect.NavigateBack -> navigateBack()
                    GroupRegisterContract.SideEffect.NavigateRegister -> navigateRegister()
                    else -> {}
                }
            }
    }
    GroupIntroductionScreen(
        groupTitle = uiState.groupRegisterInfo.groupTitle,
        onGroupTitleChange = { groupTitle ->
            viewModel.setEvent(GroupRegisterContract.Event.OnTitleChanged(groupTitle))
        },
        introduction = uiState.groupRegisterInfo.introduction,
        onIntroductionChange = { introduction ->
            viewModel.setEvent(GroupRegisterContract.Event.OnIntroductionChanged(introduction))
        },
        onNextButtonClicked = {
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateRegister)
        },
        onBackClick = {
            viewModel.sendSideEffect(GroupRegisterContract.SideEffect.NavigateBack)
            viewModel.setEvent(GroupRegisterContract.Event.OnTitleIntroductionDeleted)
        }
    )
}

/**
 * Displays the group introduction registration screen with input fields and a "Next" button.
 *
 * Shows fields for entering the group title and introduction, and enables the "Next" button only when the title is not blank. Handles user input and navigation actions via provided callbacks.
 */
@Composable
private fun GroupIntroductionScreen(
    groupTitle: String,
    onGroupTitleChange: (String) -> Unit,
    introduction: String,
    onIntroductionChange: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GroupIntroductionSection(
            groupTitle = groupTitle,
            onGroupTitleChange = onGroupTitleChange,
            introduction = introduction,
            onIntroductionChange = onIntroductionChange,
            onBackClick = onBackClick
        )

        GongBaekBasicButton(
            title = stringResource(R.string.groupregister_next),
            onClick = onNextButtonClicked,
            enabled = groupTitle.isNotBlank(),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

/**
 * Displays the UI section for entering a group title and introduction during group registration.
 *
 * Presents a top bar with a back button, a progress bar, a description, and two text fields for the group title and introduction.
 * Invokes the provided callbacks when the user edits the text fields or presses the back button.
 *
 * @param groupTitle Current value of the group title input.
 * @param onGroupTitleChange Callback invoked when the group title changes.
 * @param introduction Current value of the group introduction input.
 * @param onIntroductionChange Callback invoked when the group introduction changes.
 * @param onBackClick Callback invoked when the back button is pressed.
 * @param modifier Modifier to be applied to the section layout.
 */
@Composable
private fun GroupIntroductionSection(
    groupTitle: String,
    onGroupTitleChange: (String) -> Unit,
    introduction: String,
    onIntroductionChange: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        StartTitleTopBar(
            onLeadingIconClick = onBackClick
        )
        GongBaekProgressBar(progressPercent = 0.125f * 7f)

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            PageDescriptionSection(
                titleResId = R.string.groupregister_introduction_title,
                modifier = Modifier.padding(top = 40.dp, bottom = 28.dp)
            )

            GongBaekBasicTextField(
                value = groupTitle,
                onValueChange = onGroupTitleChange,
                gongBaekBasicTextFieldType = GongBaekBasicTextFieldType.GROUP_TITLE
            )
            Spacer(Modifier.height(28.dp))

            GongBaekBasicTextField(
                value = introduction,
                onValueChange = onIntroductionChange,
                gongBaekBasicTextFieldType = GongBaekBasicTextFieldType.GROUP_INTRODUCTION,
                modifier = Modifier
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.169f)
            )
        }
    }
}

/**
 * Displays a preview of the group introduction registration screen with default values for design and development purposes.
 */
@Preview(showBackground = true)
@Composable
private fun ShowGroupIntroductionScreen() {
    GONGBAEKTheme {
        GroupIntroductionScreen(
            groupTitle = "",
            onGroupTitleChange = {},
            introduction = "",
            onIntroductionChange = {},
            onNextButtonClicked = {},
            onBackClick = {}
        )
    }
}
