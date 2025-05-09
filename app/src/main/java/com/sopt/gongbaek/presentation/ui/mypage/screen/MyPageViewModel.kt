package com.sopt.gongbaek.presentation.ui.mypage.screen

import androidx.lifecycle.viewModelScope
import com.sopt.gongbaek.domain.repository.TokenRepository
import com.sopt.gongbaek.domain.usecase.GetMyGroupsUseCase
import com.sopt.gongbaek.domain.usecase.GetMyProfileUseCase
import com.sopt.gongbaek.domain.usecase.LogoutUseCase
import com.sopt.gongbaek.domain.usecase.WithdrawUseCase
import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getMyGroupsUseCase: GetMyGroupsUseCase,
    private val tokenRepository: TokenRepository,
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase
) : BaseViewModel<MyPageContract.State, MyPageContract.Event, MyPageContract.SideEffect>() {

    init {
        getMyProfile()
        getApplyGroups()
    }

    override fun createInitialState(): MyPageContract.State = MyPageContract.State()

    override suspend fun handleEvent(event: MyPageContract.Event) {
        when (event) {
            is MyPageContract.Event.OnGetMyProfile -> getMyProfile()
            is MyPageContract.Event.OnRegisterGroupsTabClick -> getRegisterGroups()
            is MyPageContract.Event.OnApplyGroupsTabClick -> getApplyGroups()
            is MyPageContract.Event.OnLogoutClicked -> {
                setState { copy(logoutDialogState = true) }
            }

            is MyPageContract.Event.OnWithdrawClicked -> {
                setState { copy(withdrawDialogState = true) }
            }

            is MyPageContract.Event.OnLogoutDialogConfirmClicked -> logout()

            is MyPageContract.Event.OnLogoutDialogDismissClicked -> {
                setState { copy(logoutDialogState = false) }
            }

            is MyPageContract.Event.OnWithdrawDialogConfirmClicked -> withdraw()

            is MyPageContract.Event.OnWithdrawDialogDismissClicked -> {
                setState { copy(withdrawDialogState = false) }
            }
        }
    }

    fun sendSideEffect(sideEffect: MyPageContract.SideEffect) = setSideEffect(sideEffect)

    private fun getMyProfile() {
        viewModelScope.launch {
            setState { copy(myPageLoadState = UiLoadState.Loading) }
            getMyProfileUseCase().fold(
                onSuccess = { myProfile ->
                    setState {
                        copy(
                            myPageLoadState = UiLoadState.Success,
                            myPageInfo = myProfile
                        )
                    }
                },
                onFailure = { setState { copy(myPageLoadState = UiLoadState.Error) } }
            )
        }
    }

    private fun getRegisterGroups() {
        viewModelScope.launch {
            setState { copy(registerGroupsLoadState = UiLoadState.Loading) }
            getMyGroupsUseCase(category = "REGISTER", status = true).fold(
                onSuccess = { activeGroups ->
                    setState {
                        copy(
                            myPageLoadState = UiLoadState.Success,
                            registerActiveGroups = activeGroups
                        )
                    }
                },
                onFailure = { setState { copy(registerGroupsLoadState = UiLoadState.Error) } }
            )
            getMyGroupsUseCase(category = "REGISTER", status = false).fold(
                onSuccess = { closedGroups ->
                    setState {
                        copy(
                            registerGroupsLoadState = UiLoadState.Success,
                            registerClosedGroups = closedGroups
                        )
                    }
                },
                onFailure = { setState { copy(registerGroupsLoadState = UiLoadState.Error) } }
            )
        }
    }

    private fun getApplyGroups() {
        viewModelScope.launch {
            setState { copy(applyGroupsLoadState = UiLoadState.Loading) }
            getMyGroupsUseCase(category = "APPLY", status = true).fold(
                onSuccess = { activeGroups ->
                    setState {
                        copy(
                            applyGroupsLoadState = UiLoadState.Success,
                            applyActiveGroups = activeGroups
                        )
                    }
                },
                onFailure = { setState { copy(applyGroupsLoadState = UiLoadState.Error) } }
            )
            getMyGroupsUseCase(category = "APPLY", status = false).fold(
                onSuccess = { closedGroups ->
                    setState {
                        copy(
                            applyGroupsLoadState = UiLoadState.Success,
                            applyClosedGroups = closedGroups
                        )
                    }
                },
                onFailure = { setState { copy(applyGroupsLoadState = UiLoadState.Error) } }
            )
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase().fold(
                onSuccess = {
                    setState { copy(logoutDialogState = false) }
                    tokenRepository.clearAuthTokens()
                    sendSideEffect(MyPageContract.SideEffect.NavigateLogin)
                },
                onFailure = {
                    Timber.tag("Logout").e(it, "Logout failed")
                }
            )
        }
    }

    private fun withdraw() {
        viewModelScope.launch {
            withdrawUseCase().fold(
                onSuccess = {
                    setState { copy(withdrawDialogState = false) }
                    tokenRepository.clearAuthTokens()
                    sendSideEffect(MyPageContract.SideEffect.NavigateLogin)
                },
                onFailure = {
                    Timber.tag("Withdraw").e(it, "Withdraw failed")
                }
            )
        }
    }
}
