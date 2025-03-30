package com.sopt.gongbaek.data.local.datasourceimpl

import android.content.SharedPreferences
import com.sopt.gongbaek.data.local.datasource.TokenLocalDataSource
import com.sopt.gongbaek.di.qualifier.TokenPrefs
import javax.inject.Inject

class TokenLocalDataSourceImpl @Inject constructor(
    @TokenPrefs private val sharedPreferences: SharedPreferences
) : TokenLocalDataSource {

    override var accessToken: String?
        get() = sharedPreferences.getString(ACCESS_TOKEN, "") // 토큰 값을 찾을 수 없는 경우를 위해 nullable로 선언
        set(value) = sharedPreferences.edit().putString(ACCESS_TOKEN, value).apply()

    override var refreshToken: String?
        get() = sharedPreferences.getString(REFRESH_TOKEN, null)
        set(value) = sharedPreferences.edit().putString(REFRESH_TOKEN, value).apply()

    override fun clearInfo() = sharedPreferences.edit().clear().apply()

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }
}
