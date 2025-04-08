package com.sopt.gongbaek.presentation.ui.auth.screen

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
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.type.SelectableButtonType
import com.sopt.gongbaek.presentation.ui.auth.state.MbtiState
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekSelectableButtons
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.section.PageDescriptionSection
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun MbtiRoute(
    viewModel: AuthViewModel,
    navigateSelfIntroduction: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

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
        onEnergyOptionSelected = { energy -> viewModel.setEvent(AuthContract.Event.EnergyOptionSelected(energy)) },
        onPerceptionOptionSelected = { perception -> viewModel.setEvent(AuthContract.Event.PerceptionOptionSelected(perception)) },
        onDecisionOptionSelected = { decision -> viewModel.setEvent(AuthContract.Event.DecisionOptionSelected(decision)) },
        onLifestyleOptionSelected = { lifeStyle -> viewModel.setEvent(AuthContract.Event.LifestyleOptionSelected(lifeStyle)) },
        onNextClick = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateSelfIntroduction) },
        onBackClick = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack) }
    )
}

@Composable
private fun MbtiScreen(
    uiState: MbtiState,
    onEnergyOptionSelected: (String) -> Unit,
    onPerceptionOptionSelected: (String) -> Unit,
    onDecisionOptionSelected: (String) -> Unit,
    onLifestyleOptionSelected: (String) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            GongBaekBasicButton(
                title = "다음",
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
                energyDirectionOptions = uiState.energy,
                informationGatheringOptions = uiState.perception,
                decisionMakingOptions = uiState.decision,
                lifestyleOrientationOptions = uiState.lifestyle,
                onEnergyOptionSelected = onEnergyOptionSelected,
                onPerceptionOptionSelected = onPerceptionOptionSelected,
                onDecisionOptionSelected = onDecisionOptionSelected,
                onLifestyleOptionSelected = onLifestyleOptionSelected,
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
    onEnergyOptionSelected: (String) -> Unit,
    onPerceptionOptionSelected: (String) -> Unit,
    onDecisionOptionSelected: (String) -> Unit,
    onLifestyleOptionSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        StartTitleTopBar(onClick = onBackClick)

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
                        onOptionSelected = onEnergyOptionSelected,
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
                        onOptionSelected = onPerceptionOptionSelected,
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
                        onOptionSelected = onDecisionOptionSelected,
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
                        onOptionSelected = onLifestyleOptionSelected,
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
            onEnergyOptionSelected = {},
            onPerceptionOptionSelected = {},
            onDecisionOptionSelected = {},
            onLifestyleOptionSelected = {},
            onBackClick = {},
            onNextClick = {}
        )
    }
}
