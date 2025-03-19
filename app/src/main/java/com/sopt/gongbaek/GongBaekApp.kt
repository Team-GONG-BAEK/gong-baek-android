package com.sopt.gongbaek

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.sopt.gongbaek.BuildConfig.DEBUG
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GongBaekApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setTimber()
        setDarkMode()
        initKakaoSdk()
    }

    private fun setTimber() {
        if (DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }


}
