package com.h3r3t1c.wearunitconverter.ui.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.pager.HorizontalPager
import androidx.wear.compose.foundation.pager.rememberPagerState
import androidx.wear.compose.material3.AnimatedPage
import androidx.wear.compose.material3.HorizontalPageIndicator
import androidx.wear.compose.material3.HorizontalPagerScaffold
import androidx.wear.compose.material3.PagerScaffoldDefaults
import com.h3r3t1c.wearunitconverter.ui.compose.categories.CategoriesScreen
import com.h3r3t1c.wearunitconverter.ui.compose.settings.SettingsScreen

@Composable
fun HomeScreen(navController: NavHostController) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { 2 }

    HorizontalPagerScaffold(
        pagerState = pagerState,
        pageIndicator = {
            HorizontalPageIndicator(pagerState)
        },
    ) {
        HorizontalPager(
            state = pagerState,
            flingBehavior = PagerScaffoldDefaults.snapWithSpringFlingBehavior(state = pagerState),
            beyondViewportPageCount = 0,
            rotaryScrollableBehavior = null,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)

        ) { page ->
            AnimatedPage(pageIndex = page, pagerState = pagerState) {
                when (page) {
                    0 -> CategoriesScreen(navController)
                    1 -> SettingsScreen(navController)
                }
            }
        }
    }
}