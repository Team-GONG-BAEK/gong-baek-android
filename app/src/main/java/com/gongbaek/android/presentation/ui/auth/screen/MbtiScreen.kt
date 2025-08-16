package com.gongbaek.android.presentation.ui.auth.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.gongbaek.android.R
import com.gongbaek.android.presentation.type.SelectableButtonType
import com.gongbaek.android.presentation.ui.auth.state.MbtiState
import com.gongbaek.android.presentation.ui.component.button.GongBaekBasicButton
import com.gongbaek.android.presentation.ui.component.button.GongBaekSelectableButtons
import com.gongbaek.android.presentation.ui.component.progressBar.GongBaekProgressBar
import com.gongbaek.android.presentation.ui.component.section.PageDescriptionSection
import com.gongbaek.android.presentation.ui.component.topbar.StartTitleTopBar
import com.gongbaek.android.ui.theme.GONGBAEKTheme
import com.gongbaek.android.ui.theme.GongBaekTheme

@Composable
fun MbtiRoute(
    viewModel: AuthViewModel,
    navigateSelfIntroduction: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        viewModel.setEvent(AuthContract.Event.ClearMbti)
        viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is AuthContract.SideEffect.NavigateSelfIntroduction -> navigateSelfIntroduction()
                    is AuthContract.SideEffect.NavigateBack -> navigateBack()
                    else -> {}
                }
            }
    }

    MbtiScreen(
        uiState = uiState.mbtiState,
        onMbtiFirstOptionSelected = { option -> viewModel.setEvent(AuthContract.Event.MbtiFirstOptionSelected(option)) },
        onMbtiSecondOptionSelected = { option -> viewModel.setEvent(AuthContract.Event.MbtiSecondOptionSelected(option)) },
        onMbtiThirdOptionSelected = { option -> viewModel.setEvent(AuthContract.Event.MbtiThirdOptionSelected(option)) },
        onMbtiFourthOptionSelected = { option -> viewModel.setEvent(AuthContract.Event.MbtiFourthOptionSelected(option)) },
        onNextClick = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateSelfIntroduction) },
        onBackClick = {
            viewModel.setEvent(AuthContract.Event.ClearMbti)
            viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack)
        }
    )
}

@Composable
private fun MbtiScreen(
    uiState: MbtiState,
    onMbtiFirstOptionSelected: (String) -> Unit,
    onMbtiSecondOptionSelected: (String) -> Unit,
    onMbtiThirdOptionSelected: (String) -> Unit,
    onMbtiFourthOptionSelected: (String) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            GongBaekBasicButton(
                title = stringResource(R.string.auth_mbti_button_next),
                enabled = uiState.isNextEnabled,
                onClick = onNextClick,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        },
        containerColor = GongBaekTheme.colors.white,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            MbtiScreenContent(
                energyDirectionOptions = uiState.firstLetter,
                informationGatheringOptions = uiState.secondLetter,
                decisionMakingOptions = uiState.thirdLetter,
                lifestyleOrientationOptions = uiState.forthLetter,
                onMbtiFirstOptionSelected = onMbtiFirstOptionSelected,
                onMbtiSecondOptionSelected = onMbtiSecondOptionSelected,
                onMbtiThirdOptionSelected = onMbtiThirdOptionSelected,
                onMbtiFourthOptionSelected = onMbtiFourthOptionSelected,
                onBackClick = onBackClick,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun MbtiScreenContent(
    energyDirectionOptions: String,
    informationGatheringOptions: String,
    decisionMakingOptions: String,
    lifestyleOrientationOptions: String,
    onMbtiFirstOptionSelected: (String) -> Unit,
    onMbtiSecondOptionSelected: (String) -> Unit,
    onMbtiThirdOptionSelected: (String) -> Unit,
    onMbtiFourthOptionSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        StartTitleTopBar(onLeadingIconClick = onBackClick)

        GongBaekProgressBar(progressPercent = 5 / 7f)

        Column(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.height(54.dp))

            PageDescriptionSection(
                titleResId = R.string.auth_mbti_title,
                descriptionResId = R.string.auth_mbti_description
            )

            Spacer(modifier = Modifier.height(42.dp))

            LazyColumn {
                item {
                    Text(
                        text = stringResource(R.string.auth_mbti_i_e),
                        color = GongBaekTheme.colors.gray08,
                        style = GongBaekTheme.typography.body2.sb14
                    )
                    GongBaekSelectableButtons(
                        selectableButtonType = SelectableButtonType.MBTI_FIRST,
                        onOptionSelected = onMbtiFirstOptionSelected,
                        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                        selectedOption = energyDirectionOptions
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.auth_mbti_n_s),
                        color = GongBaekTheme.colors.gray08,
                        style = GongBaekTheme.typography.body2.sb14
                    )
                    GongBaekSelectableButtons(
                        selectableButtonType = SelectableButtonType.MBTI_SECOND,
                        onOptionSelected = onMbtiSecondOptionSelected,
                        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                        selectedOption = informationGatheringOptions
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.auth_mbti_t_f),
                        color = GongBaekTheme.colors.gray08,
                        style = GongBaekTheme.typography.body2.sb14
                    )
                    GongBaekSelectableButtons(
                        selectableButtonType = SelectableButtonType.MBTI_THIRD,
                        onOptionSelected = onMbtiThirdOptionSelected,
                        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                        selectedOption = decisionMakingOptions
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.auth_mbti_p_j),
                        color = GongBaekTheme.colors.gray08,
                        style = GongBaekTheme.typography.body2.sb14
                    )
                    GongBaekSelectableButtons(
                        selectableButtonType = SelectableButtonType.MBTI_FOURTH,
                        onOptionSelected = onMbtiFourthOptionSelected,
                        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                        selectedOption = lifestyleOrientationOptions
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMbtiScreen() {
    GONGBAEKTheme {
        MbtiScreen(
            uiState = MbtiState(),
            onMbtiFirstOptionSelected = {},
            onMbtiSecondOptionSelected = {},
            onMbtiThirdOptionSelected = {},
            onMbtiFourthOptionSelected = {},
            onBackClick = {},
            onNextClick = {}
        )
    }
}
