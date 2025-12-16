package com.h3r3t1c.wearunitconverter.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.h3r3t1c.wearunitconverter.ui.compose.nav.GraphOptionConvertPath
import com.h3r3t1c.wearunitconverter.ui.compose.nav.NavDestination
import com.h3r3t1c.wearunitconverter.ui.compose.quick_convert.QuickConvertScreen
import com.h3r3t1c.wearunitconverter.ui.theme.WearUnitConverterTheme

class ConvertActivity: ComponentActivity() {
    companion object{
        const val EXTRA_FROM = "from"
        const val EXTRA_TO = "to"
        const val EXTRA_TYPE = "type"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        val type = intent.getStringExtra(EXTRA_TYPE)!!
        val from = intent.getStringExtra(EXTRA_FROM)!!
        val to = intent.getStringExtra(EXTRA_TO)!!

        setContent {
            WearUnitConverterTheme {
                val navController = rememberSwipeDismissableNavController()
                AppScaffold(
                    modifier = Modifier.background(Color.Black)
                ) {
                    SwipeDismissableNavHost(
                        navController = navController,
                        startDestination = NavDestination.QUICK_CONVERT_PATH,
                    ) {
                        composable(NavDestination.CONVERT_PATH) {
                            GraphOptionConvertPath(navController, it)
                        }
                        composable(NavDestination.QUICK_CONVERT_PATH) {
                            QuickConvertScreen(navController, type, from, to)
                        }
                    }
                }
            }
        }
    }
}