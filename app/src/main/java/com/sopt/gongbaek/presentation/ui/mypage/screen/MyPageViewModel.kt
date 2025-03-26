package com.sopt.gongbaek.presentation.ui.mypage.screen

import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

) : BaseViewModel<MyPageContract.State, MyPageContract.Event, MyPageContract.SideEffect>() {
    override fun createInitialState(): MyPageContract.State = MyPageContract.State()

    override suspend fun handleEvent(event: MyPageContract.Event) {
        when (event) {
            is MyPageContract.Event.OnRegisterGroupsTabClick -> getRegisterGroups()
            is MyPageContract.Event.OnApplyGroupsTabClick -> getApplyGroups()
        }
    }

    fun sendSideEffect(sideEffect: MyPageContract.SideEffect) = setSideEffect(sideEffect)

    private fun getRegisterGroups() {

    }

    private fun getApplyGroups() {

    }
}