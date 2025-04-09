package com.sopt.gongbaek.presentation.ui.auth.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.sopt.gongbaek.presentation.ui.auth.component.SearchButton
import com.sopt.gongbaek.presentation.ui.auth.component.YearSelectableButton
import com.sopt.gongbaek.presentation.ui.auth.state.AcademicInfoState
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.progressBar.GongBaekProgressBar
import com.sopt.gongbaek.presentation.ui.component.section.PageDescriptionSection
import com.sopt.gongbaek.presentation.ui.component.topbar.StartTitleTopBar

@Composable
fun AcademicInfoRoute(
    viewModel: AuthViewModel,
    navigateEmailVerification: () -> Unit,
    navigateUnivSearch: () -> Unit,
    navigateMajorSearch: () -> Unit,
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
                    is AuthContract.SideEffect.NavigateUnivSearch -> navigateUnivSearch()
                    is AuthContract.SideEffect.NavigateMajorSearch -> navigateMajorSearch()
                    is AuthContract.SideEffect.NavigateEmailVerification -> navigateEmailVerification()
                    else -> {}
                }
            }
    }

    AcademicInfoScreen(
        academicInfoState = uiState.academicInfoState,
        onEnterYearSelected = { selectedEnterYear -> viewModel.setEvent(AuthContract.Event.EnterYearSelected(selectedEnterYear)) },
        onSearchUniversityClicked = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateUnivSearch) },
        onSearchMajorClicked = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateMajorSearch) },
        onNextClick = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateEmailVerification) },
        onBackClick = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack) }
    )
}

@Composable
private fun AcademicInfoScreen(
    academicInfoState: AcademicInfoState,
    onEnterYearSelected: (Int) -> Unit,
    onSearchUniversityClicked: () -> Unit,
    onSearchMajorClicked: () -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AcademicInfoSelectionSection(
            academicInfoState = academicInfoState,
            onEnterYearSelected = onEnterYearSelected,
            onSearchUniversityClicked = onSearchUniversityClicked,
            onSearchMajorClicked = onSearchMajorClicked,
            onBackClick = onBackClick,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
        )

        GongBaekBasicButton(
            title = stringResource(R.string.auth_academic_info_next_button),
            enabled = academicInfoState.isAcademicInfoComplete,
            onClick = onNextClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Composable
private fun AcademicInfoSelectionSection(
    academicInfoState: AcademicInfoState,
    onBackClick: () -> Unit,
    onSearchUniversityClicked: () -> Unit,
    onSearchMajorClicked: () -> Unit,
    onEnterYearSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        StartTitleTopBar(
            onClick = onBackClick
        )

        GongBaekProgressBar(progressPercent = 1 / 7f)

        Column(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.height(54.dp))

            PageDescriptionSection(
                titleResId = R.string.auth_academic_info_title,
                descriptionResId = R.string.auth_academic_info_description
            )

            Spacer(modifier = Modifier.height(42.dp))

            SearchButton(
                buttonLabel = stringResource(R.string.auth_academic_info_university),
                searchResult = academicInfoState.university.ifEmpty { stringResource(R.string.auth_academic_info_university_search_placeholder) },
                isSearched = academicInfoState.university.isNotEmpty(),
                onSearchButtonClicked = onSearchUniversityClicked
            )

            Spacer(modifier = Modifier.height(24.dp))

            SearchButton(
                buttonLabel = stringResource(R.string.auth_academic_info_major),
                searchResult = academicInfoState.major.ifEmpty { stringResource(R.string.auth_academic_info_major_search_placeholder) },
                isSearched = academicInfoState.major.isNotEmpty(),
                onSearchButtonClicked = onSearchMajorClicked
            )

            Spacer(modifier = Modifier.height(24.dp))

            YearSelectableButton(
                selectedYear = academicInfoState.enterYear,
                onYearSelected = onEnterYearSelected
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AcademicInfoScreenPreview() {
    AcademicInfoScreen(
        academicInfoState = AcademicInfoState(),
        onNextClick = {},
        onSearchUniversityClicked = {},
        onSearchMajorClicked = {},
        onEnterYearSelected = {},
        onBackClick = {}
    )
}
