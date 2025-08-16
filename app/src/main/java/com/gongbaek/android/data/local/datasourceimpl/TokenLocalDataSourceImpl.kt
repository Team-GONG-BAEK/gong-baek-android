package com.gongbaek.android.data.local.datasourceimpl

import androidx.datastore.core.DataStore
import com.gongbaek.android.data.local.datasource.TokenLocalDataSource
import com.gongbaek.android.data.security.AuthTokens
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<AuthTokens>
) : TokenLocalDataSource {

    override suspend fun saveAuthTokens(tokens: AuthTokens) {
        dataStore.updateData { tokens }
    }

    override suspend fun getSignUpToken(): String = dataStore.data.first().signUpToken

    override suspend fun getAccessToken(): String = dataStore.data.first().accessToken

    override suspend fun getRefreshToken(): String = dataStore.data.first().refreshToken

    override suspend fun clearAuthTokens() {
        dataStore.updateData { AuthTokens("", "", "") }
    }
}
