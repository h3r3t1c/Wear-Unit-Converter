package com.h3r3t1c.wearunitconverter.ui.compose.convert

import android.icu.util.MeasureUnit
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.ui.compose.common.ColumnItemType
import com.h3r3t1c.wearunitconverter.ui.compose.common.rememberResponsiveColumnPadding
import com.h3r3t1c.wearunitconverter.ui.compose.dialogs.NumberInputDialog
import com.h3r3t1c.wearunitconverter.ui.compose.dialogs.UnitPickerDialog
import com.h3r3t1c.wearunitconverter.ui.compose.nav.NavDestination
import com.h3r3t1c.wearunitconverter.util.ConverterType
import com.h3r3t1c.wearunitconverter.util.UnitHelper

@Composable
fun ConvertToOptionsScreen(navController: NavHostController, type: ConverterType, number: String){
    val context = LocalContext.current
    val padding = rememberResponsiveColumnPadding(
        first = ColumnItemType.ListHeader,
        last = ColumnItemType.Button
    )
    val listState = rememberScalingLazyListState(
        0
    )
    var firstUnit by remember {
        mutableStateOf(type.units[0])
    }
    var secondUnit by remember {
        mutableStateOf(type.units[1])
    }
    var currentNumber by remember(number) {
        mutableStateOf(number)
    }
    ScreenScaffold(
        scrollState = listState,
        contentPadding = padding,
        edgeButton = {
            EdgeButton(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(NavDestination.getConvertPath(type, currentNumber, firstUnit, secondUnit))
                },
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                    contentDescription = null
                )
            }
        }
    ) {
        ScalingLazyColumn(
            state = listState,
            contentPadding = padding,
            autoCentering = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            item{
                ListHeader {
                    Text(ConverterType.toDisplayName(context, type), textAlign = TextAlign.Center)
                }
            }
            item{
                NumberButton(currentNumber) {
                    currentNumber = it
                }
            }
            item{
                UnitButton(stringResource(R.string.from), firstUnit, type) {
                    if(secondUnit == it)
                        secondUnit = firstUnit
                    firstUnit = it
                }
            }
            item{
                UnitButton(stringResource(R.string.to), secondUnit, type) {
                    if(firstUnit == it)
                        firstUnit = secondUnit
                    secondUnit = it
                }
            }
        }
    }
}
@Composable
private fun NumberButton(number: String, onChange: (String) -> Unit){
    var showDialog by remember {
        mutableStateOf(false)
    }
    Button(
        onClick = {
            showDialog = true
        },
        label = {
            Text(text = number)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.filledTonalButtonColors()
    )
    NumberInputDialog(showDialog, number, {showDialog = false}) {
        showDialog = false
        onChange(it.toString())
    }
}
@Composable
private fun UnitButton(title: String, unit: MeasureUnit, type: ConverterType, onChange: (MeasureUnit) -> Unit){
    var showDialog by remember {
        mutableStateOf(false)
    }
    Button(
        onClick = {
            showDialog = true
        },
        label = {
            Text(text = title)
        },
        secondaryLabel = {
            Text(text = UnitHelper.unitToString(unit, true), maxLines = 3)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.filledTonalButtonColors()
    )
    UnitPickerDialog(showDialog, unit, type, {showDialog = false}){
        showDialog = false
        onChange(it)
    }
}