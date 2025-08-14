package com.gongbaek.android.presentation.ui.home.component.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gongbaek.android.R
import com.gongbaek.android.presentation.type.GongBaekWebView
import com.gongbaek.android.presentation.util.extension.clickableWithoutRipple
import com.gongbaek.android.presentation.util.openWebView

@Composable
fun HomeBannerSection(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.img_home_banner),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(360f / 130f)
            .clickableWithoutRipple {
                openWebView(context, GongBaekWebView.HOME_BANNER.url)
            }
    )
}

@Preview
@Composable
private fun HomeBannerSectionPreview() {
    HomeBannerSection()
}
