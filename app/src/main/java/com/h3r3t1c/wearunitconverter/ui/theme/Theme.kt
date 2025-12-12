package com.h3r3t1c.wearunitconverter.ui.theme

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.material3.ColorScheme
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.dynamicColorScheme

@Composable
fun WearUnitConverterTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = ColorScheme()
    val context = LocalContext.current
    val appColorScheme = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA ) {
        dynamicColorScheme(context)?: colorScheme
    } else colorScheme
    MaterialTheme(
        content = content,
        colorScheme = appColorScheme
    )
}