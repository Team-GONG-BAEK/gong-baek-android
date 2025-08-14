package com.gongbaek.android.presentation.ui.onboarding.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gongbaek.android.R
import com.gongbaek.android.presentation.ui.component.button.GongBaekBasicButton
import com.gongbaek.android.presentation.ui.component.topbar.StartTitleTopBar
import com.gongbaek.android.ui.theme.GONGBAEKTheme
import com.gongbaek.android.ui.theme.GongBaekTheme
import kotlinx.coroutines.launch

@Composable
fun OnboardingRoute(
    navigateAuth: () -> Unit
) {
    val pages = listOf(1, 2, 3)
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()
    var backPressedTime by rememberSaveable { mutableLongStateOf(0L) }
    val backPressThreshold = 2000
    val context = LocalContext.current

    val currentPage by remember {
        derivedStateOf { pagerState.currentPage }
    }

    val onBackClick = remember(currentPage) {
        {
            if (currentPage > 0) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(currentPage - 1)
                }
            }
        }
    }

    BackHandler {
        if (currentPage == 0) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - backPressedTime <= backPressThreshold) {
                (context as? Activity)?.finish()
            } else {
                backPressedTime = currentTime
            }
        } else {
            onBackClick()
        }
    }

    OnboardingScreen(
        currentPage = currentPage,
        pageCount = pages.size,
        pagerState = pagerState,
        onBackClick = onBackClick,
        onNextClick = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(currentPage + 1)
            }
        },
        onStartClick = navigateAuth
    )
}

@Composable
fun OnboardingScreen(
    currentPage: Int,
    pageCount: Int,
    pagerState: PagerState,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onStartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GongBaekTheme.colors.white),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StartTitleTopBar(
            isLeadingIconIncluded = currentPage != 0,
            onLeadingIconClick = onBackClick
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> OnboardingScreen1()
                1 -> OnboardingScreen2()
                2 -> OnboardingScreen3()
            }
        }

        Row(
            modifier = Modifier.padding(vertical = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(pageCount) { iteration ->
                val isSelected = currentPage == iteration
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (isSelected) {
                                GongBaekTheme.colors.gray08
                            } else {
                                GongBaekTheme.colors.gray03
                            }
                        )
                        .size(6.dp)
                )
            }
        }

        GongBaekBasicButton(
            title = if (currentPage == pageCount - 1) {
                stringResource(R.string.onboarding_button_start)
            } else {
                stringResource(R.string.onboarding_button_next)
            },
            onClick = {
                if (currentPage < pageCount - 1) {
                    onNextClick()
                } else {
                    onStartClick()
                }
            },
            enabled = true,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreen0Preview() {
    GONGBAEKTheme {
        OnboardingRoute { }
    }
}
