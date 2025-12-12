package com.h3r3t1c.wearunitconverter.ui.compose.common


import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

@Composable
fun FontScaleIndependent(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalDensity provides Density(
            density = LocalDensity.current.density,
            fontScale = 1f,
        ),
    ) {
        content()
    }
}