package com.sopt.gongbaek.presentation.ui.splash

import com.sopt.gongbaek.domain.repository.TokenRepository
import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : BaseViewModel<SplashContract.State, SplashContract.Event, SplashContract.SideEffect>() {

    override fun createInitialState(): SplashContract.State = SplashContract.State()

    override suspend fun handleEvent(event: SplashContract.Event) {
        when (event) {
            is SplashContract.Event.ValidateAutoLogin -> validateAutoLogin()
        }
    }

    fun sendSideEffect(sideEffect: SplashContract.SideEffect) = setSideEffect(sideEffect)

    private fun validateAutoLogin() {
        val hasTokens = tokenRepository.getAccessToken()?.isNotBlank() == true
        if (hasTokens) {
            setSideEffect(SplashContract.SideEffect.NavigateHome)
        } else {
            setSideEffect(SplashContract.SideEffect.NavigateSocialLogin)
        }
    }
}
