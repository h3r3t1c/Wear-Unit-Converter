package com.h3r3t1c.wearunitconverter.ui.compose.quick_convert

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.h3r3t1c.wearunitconverter.ext.findActivity
import com.h3r3t1c.wearunitconverter.ui.compose.dialogs.NumberInputDialog
import com.h3r3t1c.wearunitconverter.ui.compose.nav.NavDestination

@Composable
fun QuickConvertScreen(navController: NavHostController, type: String, from: String, to: String) {
    val context = LocalContext.current
    NumberInputDialog(true, onDismiss = {
        context.findActivity().finish()
    }) {
        navController.popBackStack()
        navController.navigate(NavDestination.getConvertPath(type, it, from, to))
    }
}