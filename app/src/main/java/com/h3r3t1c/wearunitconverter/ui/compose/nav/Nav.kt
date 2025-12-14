package com.h3r3t1c.wearunitconverter.ui.compose.nav

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.h3r3t1c.wearunitconverter.ui.compose.convert.ConvertMainScreen
import com.h3r3t1c.wearunitconverter.ui.compose.convert.ConvertToOptionsScreen
import com.h3r3t1c.wearunitconverter.ui.compose.home.HomeScreen
import eu.hansolo.unit.converter.Converter

@Composable
fun Nav(destination: String) {
    val navController = rememberSwipeDismissableNavController()
    AppScaffold(
        modifier = Modifier.background(Color.Black)
    ) {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = destination
        ) {
            composable(NavDestination.HOME) {
                HomeScreen(navController)
            }
            composable(NavDestination.CONVERT_PATH) {
                val type = it.arguments?.getString("type")
                val number = it.arguments?.getString("inputNumber")
                val firstUnit = it.arguments?.getString("from")!!
                val secondUnit = it.arguments?.getString("to")!!
                ConvertMainScreen(navController, type!!, number!!, firstUnit, secondUnit)
            }
            composable(NavDestination.CONVERT_TO_OPTIONS_PATH) {
                val type = it.arguments?.getString("type")!!
                val number = it.arguments?.getString("inputNumber")!!
                ConvertToOptionsScreen(navController, Converter.Category.valueOf(type), number)
            }
        }
    }
}