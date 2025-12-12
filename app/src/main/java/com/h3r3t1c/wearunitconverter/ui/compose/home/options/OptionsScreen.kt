package com.h3r3t1c.wearunitconverter.ui.compose.home.options

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.ui.compose.common.ColumnItemType
import com.h3r3t1c.wearunitconverter.ui.compose.common.rememberResponsiveColumnPadding
import com.h3r3t1c.wearunitconverter.ui.compose.dialogs.NumberInputDialog
import com.h3r3t1c.wearunitconverter.ui.compose.nav.NavDestination

@Composable
fun OptionsScreen(navController: NavHostController){
    val viewModel = viewModel<ConvertOptionsViewModel>()
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
            item("header"){
                ListHeader {
                    Text(text = stringResource(R.string.unit_conversion))
                }
            }
            items(viewModel.options, key = {it.type.name}){
                ListOption(it){
                    //navController.navigate(NavDestination.getConvertPath(it.type))
                    viewModel.selectedOption = it.type
                }
            }
        }
        Dialogs(viewModel, navController)
    }
}
@Composable
fun Dialogs(viewModel: ConvertOptionsViewModel, navController: NavHostController){
    NumberInputDialog(
        visible = viewModel.selectedOption != null,
        onDismiss = { viewModel.selectedOption = null }
    ) {
        navController.navigate(NavDestination.getConvertToOptionsPath(viewModel.selectedOption!!, it))
        viewModel.selectedOption = null
    }
}
@Composable
private fun ListOption(option: Option, onClick: () -> Unit){
    Button(
        onClick = onClick,
        label = {
            Text(text = stringResource(option.titleResource))
        },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(option.iconResource),
                contentDescription = stringResource(option.titleResource)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.filledTonalButtonColors()
    )
}