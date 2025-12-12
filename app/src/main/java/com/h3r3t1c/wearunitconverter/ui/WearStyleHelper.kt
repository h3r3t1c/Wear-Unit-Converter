package com.h3r3t1c.wearunitconverter.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

object WearStyleHelper {
    const val LARGE_SCREEN_WIDTH_DP_THRESHOLD = 225

    @Composable
    fun isLargeScreen() = LocalConfiguration.current.screenWidthDp > LARGE_SCREEN_WIDTH_DP_THRESHOLD

    fun isLargeScreen(context: Context) = context.resources.configuration.screenWidthDp > LARGE_SCREEN_WIDTH_DP_THRESHOLD
}