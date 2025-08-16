package com.gongbaek.android.presentation.type

import com.gongbaek.android.R

enum class SettingServiceGuideItem(
    val titleResId: Int,
    val url: String?
) {
    NOTICE(R.string.setting_notice, GongBaekWebView.NOTICE.url),
    FAQ(R.string.setting_faq, GongBaekWebView.FAQ.url),
    PRIVACY_POLICY(R.string.setting_privacy_policy, GongBaekWebView.PRIVACY_POLICY.url),
    TERMS_OF_SERVICE(R.string.setting_terms_of_service, GongBaekWebView.TERMS_OF_SERVICE.url),
    VERSION_INFO(R.string.setting_version_info, null)
}
