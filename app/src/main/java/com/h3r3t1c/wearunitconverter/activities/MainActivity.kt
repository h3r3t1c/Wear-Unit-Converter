package com.h3r3t1c.wearunitconverter.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.h3r3t1c.wearunitconverter.ui.compose.nav.Nav
import com.h3r3t1c.wearunitconverter.ui.compose.nav.NavDestination
import com.h3r3t1c.wearunitconverter.ui.theme.WearUnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            WearUnitConverterTheme {
                Nav(NavDestination.HOME)
            }
        }
    }
}