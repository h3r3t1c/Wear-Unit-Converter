package com.h3r3t1c.wearunitconverter.ui.compose.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.OpenOnPhoneDialog
import androidx.wear.compose.material3.OpenOnPhoneDialogDefaults
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.openOnPhoneDialogCurvedText
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.ui.compose.common.ColumnItemType
import com.h3r3t1c.wearunitconverter.ui.compose.common.FontScaleIndependent
import com.h3r3t1c.wearunitconverter.ui.compose.common.rememberResponsiveColumnPadding

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun SettingsScreen(navController: NavHostController){
    val context = LocalContext.current
    val viewModel = viewModel<SettingsViewModel>(factory = SettingsViewModel.getFactory(context))
    val padding = rememberResponsiveColumnPadding(
        first = ColumnItemType.ListHeader,
        last = ColumnItemType.Button
    )
    val listState = rememberScalingLazyListState(
        0
    )
    ScreenScaffold(
        scrollState = listState,
        contentPadding = padding
    ) {
        ScalingLazyColumn(
            state = listState,
            contentPadding = padding,
            autoCentering = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            items(viewModel.options, key = {it.key}){
                when(it) {
                    is SettingsOption.Header -> ListHeader {
                        Text(text = stringResource(it.stringResource))
                    }

                    is SettingsOption.Info -> {
                        Info(it)
                    }

                    is SettingsOption.ClickOption -> {
                        ClickOption(it)
                    }
                }
            }
        }
        FontScaleIndependent {
            val dialogStyle = OpenOnPhoneDialogDefaults.curvedTextStyle
            OpenOnPhoneDialog(
                visible = viewModel.showContinueOnPhoneDialog,
                onDismissRequest = { viewModel.showContinueOnPhoneDialog = false },
                curvedText = { openOnPhoneDialogCurvedText(text = context.getString(R.string.continue_on_phone), style = dialogStyle) },
            )
        }
    }
}
@Composable
private fun ClickOption(option: SettingsOption.ClickOption){
    Button(
        onClick = option.click,
        label = {
            Text(text = stringResource(option.stringResource))
        },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(option.icon),
                contentDescription = stringResource(option.stringResource),
            )
        },
        colors = ButtonDefaults.filledTonalButtonColors(),
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
private fun Info(option: SettingsOption.Info){
    Button(
        onClick = {},
        label = {
            Text(text = stringResource(option.stringResource))
        },
        secondaryLabel = {
            Text(text = option.value)
        },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(option.icon),
                contentDescription = stringResource(option.stringResource),
            )
        },
        colors = ButtonDefaults.filledTonalButtonColors(),
        modifier = Modifier.fillMaxWidth()
    )
}