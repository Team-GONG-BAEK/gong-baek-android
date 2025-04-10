package com.sopt.gongbaek.presentation.type

import com.sopt.gongbaek.R

enum class SettingServiceGuideItem(
    val titleResId: Int,
    val url: String?
) {
    NOTICE(R.string.setting_notice, GongBaekWebView.NOTICE.url),
    PRIVACY_POLICY(R.string.setting_privacy_policy, GongBaekWebView.PRIVACY_POLICY.url),
    TERMS_OF_SERVICE(R.string.setting_terms_of_service, GongBaekWebView.TERMS_OF_SERVICE.url),
    VERSION_INFO(R.string.setting_version_info, null)
}
