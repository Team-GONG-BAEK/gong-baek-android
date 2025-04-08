package com.sopt.gongbaek.presentation.ui.auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.type.GongBaekBasicTextFieldType
import com.sopt.gongbaek.presentation.type.SelectableButtonType
import com.sopt.gongbaek.presentation.ui.auth.state.NicknameGenderState
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekSelectableButtons
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.section.PageDescriptionSection
import com.sopt.gongbaek.presentation.ui.component.textfield.GongBaekBasicTextField
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun NicknameGenderRoute(
    viewModel: AuthViewModel,
    navigateSelectProfile: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is AuthContract.SideEffect.NavigateBack -> navigateBack()
                    is AuthContract.SideEffect.NavigateSelectProfile -> navigateSelectProfile()
                    else -> {}
                }
            }
    }

    NicknameGenderScreen(
        uiState = uiState.nicknameGenderState,
        onNicknameChanged = { nickname -> viewModel.setEvent(AuthContract.Event.NicknameChanged(nickname)) },
        onGenderSelected = { gender -> viewModel.setEvent(AuthContract.Event.GenderSelected(gender)) },
        onNextClick = { viewModel.setEvent(AuthContract.Event.ValidateNickname) },
        onBackClick = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack) }
    )
}

@Composable
private fun NicknameGenderScreen(
    uiState: NicknameGenderState,
    onGenderSelected: (String) -> Unit,
    onNicknameChanged: (String) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NickNameInputSection(
            nickname = uiState.nickname,
            onNicknameChanged = onNicknameChanged,
            errorMessage = uiState.nicknameErrorMessage,
            selectedGender = uiState.gender,
            onSelectedGender = onGenderSelected,
            onBackClick = onBackClick,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
        )

        GongBaekBasicButton(
            title = "다음",
            enabled = uiState.isNextEnabled,
            onClick = onNextClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Composable
private fun NickNameInputSection(
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    errorMessage: String?,
    selectedGender: String,
    onSelectedGender: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        StartTitleTopBar(onClick = onBackClick)

        GongBaekProgressBar(progressPercent = 3 / 7f)

        Column(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.height(54.dp))

            PageDescriptionSection(
                titleResId = R.string.auth_nickname_gender_title,
                descriptionResId = R.string.auth_nickname_gender_description
            )

            Spacer(modifier = Modifier.height(42.dp))

            GongBaekBasicTextField(
                value = nickname,
                onValueChange = onNicknameChanged,
                gongBaekBasicTextFieldType = GongBaekBasicTextFieldType.NICKNAME,
                isError = !errorMessage.isNullOrEmpty(),
                errorMessage = errorMessage.orEmpty()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "성별",
                    color = GongBaekTheme.colors.gray08,
                    style = GongBaekTheme.typography.body2.sb14
                )

                GongBaekSelectableButtons(
                    selectableButtonType = SelectableButtonType.GENDER,
                    onOptionSelected = onSelectedGender,
                    selectedOption = selectedGender
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NicknameGenderScreenPreview() {
    NicknameGenderScreen(
        uiState = NicknameGenderState(),
        onNicknameChanged = {},
        onGenderSelected = {},
        onNextClick = {},
        onBackClick = {}
    )
}
