package com.sopt.gongbaek.data.remote.util

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.sopt.gongbaek.data.local.datasource.TokenLocalDataSource
import com.sopt.gongbaek.domain.usecase.ReissueTokenUseCase
import com.sopt.gongbaek.presentation.ui.main.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Provider

class AuthInterceptor @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val reissueTokenUseCaseProvider: Provider<ReissueTokenUseCase>,
    @ApplicationContext private val context: Context
) : Interceptor {

    private val mutex = Mutex()
    private var tokenRefreshJob: Deferred<Boolean>? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            val originalRequest = chain.request()
            val url = originalRequest.url.toString()

            if (shouldAddAuthorization(url)) {
                val response = proceedWithAuthorization(chain, originalRequest)
                if (response.code == ACCESS_TOKEN_EXPIRED) {
                    response.close()
                    handleAccessTokenExpired(chain, originalRequest)
                }
                response
            } else {
                chain.proceed(originalRequest)
            }
        }
    }

    private fun shouldAddAuthorization(url: String): Boolean {
        return !url.contains("api/v1/login") &&
            !url.contains("/api/v1/reissue/token")
    }

    private fun proceedWithAuthorization(chain: Interceptor.Chain, request: Request): Response {
        val authRequest = addAuthorizationHeader(request)
        return chain.proceed(authRequest)
    }

    private fun addAuthorizationHeader(request: Request): Request =
        request.newBuilder()
            .addHeader(AUTHORIZATION, "$BEARER ${tokenLocalDataSource.accessToken}")
            .build()

    private fun handleAccessTokenExpired(chain: Interceptor.Chain, originalRequest: Request): Response {
        return runBlocking {
            mutex.withLock {
                if (tokenRefreshJob?.isCompleted != false) { // 실패했거나 완료된 경우
                    tokenRefreshJob = async { reissueToken() }
                }
            }

            val tokenRefreshed = tokenRefreshJob?.await() ?: false

            if (tokenRefreshed) {
                proceedWithAuthorization(chain, originalRequest) // 새 토큰으로 요청 재시도
            } else {
                clearUserInfoAndLogout()
                throw IOException("Token expired and reissue failed")
            }
        }
    }

    private suspend fun reissueToken(): Boolean {
        return try {
            val reissueTokenUseCase = reissueTokenUseCaseProvider.get()
            reissueTokenUseCase(tokenLocalDataSource.refreshToken ?: "").onSuccess { data ->
                if (data.accessToken.isEmpty() || data.refreshToken.isEmpty()) {
                    Timber.e("Token reissue returned empty tokens")
                    clearUserInfoAndLogout()
                } else {
                    updateTokens(data.accessToken, data.refreshToken)
                }
            }.onFailure { throwable ->
                Timber.e("Token reissue failed: ${throwable.message}")
                clearUserInfoAndLogout()
            }
            true
        } catch (t: Throwable) {
            Timber.e("Unexpected error during token reissue: ${t.message}")
            clearUserInfoAndLogout()
            false
        }
    }

    private fun updateTokens(newAccessToken: String, newRefreshToken: String) {
        Timber.d("NEW ACCESS TOKEN : $newAccessToken")
        Timber.d("NEW REFRESH TOKEN : $newRefreshToken")
        tokenLocalDataSource.apply {
            accessToken = newAccessToken
            refreshToken = newRefreshToken
        }
    }

    private fun clearUserInfoAndLogout() {
        tokenLocalDataSource.clearInfo()
        Handler(Looper.getMainLooper()).post {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("NAVIGATE_TO_LOGIN", true)
            }
            context.startActivity(intent)
        }
    }

    companion object {
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
        private const val ACCESS_TOKEN_EXPIRED = 4012
    }
}
