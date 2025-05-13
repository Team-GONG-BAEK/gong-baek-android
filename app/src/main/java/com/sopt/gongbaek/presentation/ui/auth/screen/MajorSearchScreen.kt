package com.sopt.gongbaek.presentation.ui.auth.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.ui.auth.component.EmptySearchResultView
import com.sopt.gongbaek.presentation.ui.auth.component.SearchResultSection
import com.sopt.gongbaek.presentation.ui.auth.state.AcademicInfoState
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.presentation.ui.component.topbar.CenterTitleTopBar
import com.sopt.gongbaek.presentation.util.extension.clickableWithoutRipple
import com.sopt.gongbaek.presentation.util.extension.roundedBackgroundWithBorder
import com.sopt.gongbaek.ui.theme.GONGBAEKTheme
import com.sopt.gongbaek.ui.theme.GongBaekTheme

/**
 * Connects the major search UI with the authentication ViewModel, handling state, events, and navigation.
 *
 * Observes UI state and side effects from the ViewModel, manages back navigation to clear the selected major, and passes relevant event handlers to the major search screen.
 *
 * @param viewModel The authentication ViewModel providing state and event handling for the major search flow.
 * @param navigateBack Callback invoked to navigate back in the navigation stack.
 */
@Composable
fun MajorSearchRoute(
    viewModel: AuthViewModel,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    BackHandler {
        viewModel.setEvent(AuthContract.Event.ClearMajor)
        viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is AuthContract.SideEffect.NavigateBack -> navigateBack()
                    else -> {}
                }
            }
    }

    MajorSearchScreen(
        academicInfoState = uiState.academicInfoState,
        onSearchQueryChanged = { query -> viewModel.setEvent(AuthContract.Event.MajorSearchQueryChanged(query)) },
        onSearchButtonClicked = { viewModel.setEvent(AuthContract.Event.MajorSearchClicked) },
        onMajorSelected = { selectedMajor -> viewModel.setEvent(AuthContract.Event.MajorSelected(selectedMajor)) },
        onComplete = { viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack) },
        onCloseClick = {
            viewModel.setEvent(AuthContract.Event.ClearMajor)
            viewModel.sendSideEffect(AuthContract.SideEffect.NavigateBack)
        }
    )
}

/**
 * Displays the UI for searching and selecting an academic major, including direct registration and completion actions.
 *
 * Presents a search field, search results, and options to directly register a new major or apply the selected one.
 * Invokes callbacks when the search query changes, a search is performed, a major is selected, the close icon is clicked, or the completion action is triggered.
 *
 * @param academicInfoState The current state of academic information and search results.
 * @param onSearchQueryChanged Called when the search query input changes.
 * @param onSearchButtonClicked Called when the search action is triggered.
 * @param onMajorSelected Called when a major is selected from the results or via direct registration.
 * @param onCloseClick Called when the close icon in the top bar is clicked.
 * @param onComplete Called when the user completes the selection or registration process.
 */
@Composable
private fun MajorSearchScreen(
    academicInfoState: AcademicInfoState,
    onSearchQueryChanged: (String) -> Unit,
    onSearchButtonClicked: () -> Unit,
    onMajorSelected: (String) -> Unit,
    onCloseClick: () -> Unit,
    onComplete: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterTitleTopBar(
                centerTitleResId = R.string.topbar_search,
                trailingIconResId = R.drawable.ic_x_48,
                textColor = GongBaekTheme.colors.gray10,
                textStyle = GongBaekTheme.typography.body1.sb16,
                onTrailingIconClick = onCloseClick
            )
        },
        bottomBar = {
            Column {
                if (academicInfoState.searchedMajors?.isEmpty() == true) {
                    DirectRegistrationButton(
                        major = academicInfoState.majorSearchQuery,
                        onClick = {
                            onMajorSelected(academicInfoState.majorSearchQuery)
                            onComplete()
                        },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                GongBaekBasicButton(
                    title = stringResource(R.string.auth_academic_info_apply_button),
                    enabled = academicInfoState.isMajorSearchComplete,
                    onClick = onComplete,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        },
        containerColor = GongBaekTheme.colors.white,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(top = 12.dp)
            ) {
                SearchTextField(
                    value = academicInfoState.majorSearchQuery,
                    onValueChange = onSearchQueryChanged,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onSearchButtonClicked = onSearchButtonClicked
                )

                Spacer(modifier = Modifier.height(14.dp))

                val keyboardController = LocalSoftwareKeyboardController.current
                val majors = academicInfoState.searchedMajors
                when {
                    majors == null -> {}
                    majors.isEmpty() -> EmptySearchResultView()
                    else -> {
                        SearchResultSection(
                            searchResults = majors,
                            selectedItem = academicInfoState.major,
                            onItemSelected = { query ->
                                keyboardController?.hide()
                                onMajorSelected(query)
                            }
                        )
                    }
                }
            }
        }
    )
}

/**
 * Displays a text input field for searching academic majors with a search icon and error handling.
 *
 * Shows a labeled input with a placeholder, manages focus and error state, and triggers search actions via the keyboard or search icon.
 *
 * @param value The current text input value.
 * @param onValueChange Called when the text input changes.
 * @param isError Whether the input is in an error state.
 * @param onSearchButtonClicked Called when the search action is triggered.
 * @param onErrorChange Called to update the error state.
 */
@Composable
private fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    onSearchButtonClicked: () -> Unit = {},
    onErrorChange: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.auth_academic_info_major),
                color = GongBaekTheme.colors.gray08,
                style = GongBaekTheme.typography.body2.sb14
            )
        }

        var isFocused by remember { mutableStateOf(false) }

        val textStyle = GongBaekTheme.typography.body1.m16.copy(
            color = GongBaekTheme.colors.gray10
        )

        val keyboardController = LocalSoftwareKeyboardController.current

        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
                if (isError) {
                    onErrorChange(false)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .roundedBackgroundWithBorder(
                    cornerRadius = 6.dp,
                    backgroundColor = GongBaekTheme.colors.gray01,
                    borderColor = if (isFocused) GongBaekTheme.colors.gray10 else GongBaekTheme.colors.gray01,
                    borderWidth = 1.dp
                )
                .padding(start = 16.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            singleLine = true,
            textStyle = textStyle,
            cursorBrush = SolidColor(GongBaekTheme.colors.gray05),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSearchButtonClicked()
                }
            )
        ) { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(R.string.auth_academic_info_major_search_placeholder),
                            color = GongBaekTheme.colors.gray04,
                            style = GongBaekTheme.typography.body1.m16
                        )
                    }
                    innerTextField()
                }

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search_black_48),
                    contentDescription = null,
                    tint = GongBaekTheme.colors.gray10,
                    modifier = Modifier.clickableWithoutRipple(
                        enabled = value.isNotEmpty(),
                        onClick = onSearchButtonClicked
                    )
                )
            }
        }
    }
}

@Composable
private fun DirectRegistrationButton(
    major: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .roundedBackgroundWithBorder(
                cornerRadius = 3.dp,
                backgroundColor = GongBaekTheme.colors.gray02
            )
            .clickableWithoutRipple { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = GongBaekTheme.colors.mainOrange)) {
                    append("\"${major}\"")
                }
                append(stringResource(R.string.auth_academic_info_direct_register))
            },
            color = GongBaekTheme.colors.gray08,
            style = GongBaekTheme.typography.caption2.m12,
            maxLines = Int.MAX_VALUE
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_plus_black_18),
            contentDescription = null,
            tint = GongBaekTheme.colors.gray10
        )
    }
}

@Preview
@Composable
private fun DirectRegistrationButtonPreview() {
    DirectRegistrationButton(
        major = "컴퓨터공학과",
        onClick = {}
    )
}

/**
 * Displays a preview of the MajorSearchScreen composable with default state and empty event handlers.
 */
@Preview(showBackground = true)
@Composable
private fun MajorSearchScreenPreview() {
    GONGBAEKTheme {
        MajorSearchScreen(
            academicInfoState = AcademicInfoState(),
            onSearchQueryChanged = {},
            onSearchButtonClicked = {},
            onMajorSelected = {},
            onComplete = {},
            onCloseClick = {}
        )
    }
}
