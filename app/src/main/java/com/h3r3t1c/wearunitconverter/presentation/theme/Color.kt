package com.h3r3t1c.wearunitconverter.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Red400 = Color(0xFFCF6679)

val Blue500 = Color(0xff2196F3)
val Blue700 = Color(0xff1976D2)
val Orange500 = Color(0xffFFC107)
val Amber700 = Color(0xffFFA000)

internal val wearColorPalette: Colors = Colors(
    primary = Blue500,
    primaryVariant = Blue700,
    secondary = Orange500,
    secondaryVariant = Amber700,
    error = Red400,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onError = Red400
)