package com.sopt.gongbaek.presentation.ui.mypage.screen

import androidx.lifecycle.viewModelScope
import com.sopt.gongbaek.domain.usecase.GetMyGroupsUseCase
import com.sopt.gongbaek.domain.usecase.GetMyProfileUseCase
import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getMyGroupsUseCase: GetMyGroupsUseCase
) : BaseViewModel<MyPageContract.State, MyPageContract.Event, MyPageContract.SideEffect>() {
    override fun createInitialState(): MyPageContract.State = MyPageContract.State()

    override suspend fun handleEvent(event: MyPageContract.Event) {
        when (event) {
            is MyPageContract.Event.OnRegisterGroupsTabClick -> getRegisterGroups()
            is MyPageContract.Event.OnApplyGroupsTabClick -> getApplyGroups()
        }
    }

    fun sendSideEffect(sideEffect: MyPageContract.SideEffect) = setSideEffect(sideEffect)

    private fun getMyProfile() {
        viewModelScope.launch {
            setState { copy(myPageLoadState = UiLoadState.Loading) }
            getMyProfileUseCase().fold(
                onSuccess = { myProfile ->
                    setState { copy(myPageInfo = myProfile) }
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
                    setState { copy(registerActiveGroups = activeGroups) }
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
                    setState { copy(applyActiveGroups = activeGroups) }
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
}
