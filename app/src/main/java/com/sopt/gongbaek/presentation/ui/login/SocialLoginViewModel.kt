package com.sopt.gongbaek.presentation.ui.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.sopt.gongbaek.domain.repository.TokenRepository
import com.sopt.gongbaek.domain.usecase.LoginUseCase
import com.sopt.gongbaek.presentation.util.base.BaseViewModel
import com.sopt.gongbaek.presentation.util.base.UiLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class SocialLoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenRepository: TokenRepository
) : BaseViewModel<SocialLoginContract.State, SocialLoginContract.Event, SocialLoginContract.SideEffect>() {

    override fun createInitialState() = SocialLoginContract.State()

    override suspend fun handleEvent(event: SocialLoginContract.Event) {
        when (event) {
            is SocialLoginContract.Event.OnKaKaoLoginClick -> {
                loginWithKakao(event.context)
            }
        }
    }

    fun sendSideEffect(sideEffect: SocialLoginContract.SideEffect) = setSideEffect(sideEffect)

    private fun loginWithKakao(context: Context) {
        viewModelScope.launch {
            setState { copy(signInState = UiLoadState.Loading) }
            runCatching {
                suspendCancellableCoroutine { continuation ->
                    performKakaoLogin(context) { token, error ->
                        when {
                            error != null -> continuation.resumeWithException(error)
                            token != null -> continuation.resume(token)
                        }
                    }
                }
            }.onSuccess {
                setState { copy(kakaoToken = it.accessToken) }
                login()
            }.onFailure {
                setState { copy(signInState = UiLoadState.Error) }
            }
        }
    }

    private fun performKakaoLogin(context: Context, callback: (OAuthToken?, Throwable?) -> Unit) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) return@loginWithKakaoTalk
                if (token != null) callback(token, null) else UserApiClient.instance.loginWithKakaoAccount(context = context, callback = callback)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context = context, callback = callback)
        }
    }

    private fun login() {
        viewModelScope.launch {
            val token = currentState.kakaoToken
            if (token.isBlank()) {
                setState { copy(signInState = UiLoadState.Error) }
            }
            loginUseCase(kakaoToken = token, platform = "KAKAO").fold(
                onSuccess = { response ->
                    setState { copy(signInState = UiLoadState.Success) }
                    setSideEffect(
                        if (response.userId != null) {
                            tokenRepository.saveAuthTokens(
                                signUpToken = "",
                                accessToken = response.accessToken,
                                refreshToken = response.refreshToken
                            )
                            SocialLoginContract.SideEffect.NavigateHome
                        } else {
                            tokenRepository.saveAuthTokens(
                                signUpToken = response.accessToken,
                                accessToken = "",
                                refreshToken = ""
                            )
                            SocialLoginContract.SideEffect.NavigateTermsOfService
                        }
                    )
                },
                onFailure = { setState { copy(signInState = UiLoadState.Error) } }
            )
        }
    }
}
