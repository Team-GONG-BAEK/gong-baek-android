package com.sopt.gongbaek.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.sopt.gongbaek.domain.repository.TokenRepository
import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : BaseViewModel<SplashContract.State, SplashContract.Event, SplashContract.SideEffect>() {

    override fun createInitialState(): SplashContract.State = SplashContract.State()

    override suspend fun handleEvent(event: SplashContract.Event) {
        when (event) {
            is SplashContract.Event.ValidateAutoLogin -> validateAutoLogin()

            is SplashContract.Event.ResetAutoLogin -> setState { copy(autoLogin = null) }
        }
    }

    fun sendSideEffect(sideEffect: SplashContract.SideEffect) = setSideEffect(sideEffect)

    private fun validateAutoLogin() {
        viewModelScope.launch {
            val hasTokens = tokenRepository.getAccessToken().isNotBlank() && tokenRepository.getRefreshToken().isNotBlank()
            setState { copy(autoLogin = hasTokens) }
        }
    }
}
