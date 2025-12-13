package com.h3r3t1c.wearunitconverter.ui.compose.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.Dialog
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.RadioButton
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.h3r3t1c.wearunitconverter.ui.compose.common.ColumnItemType
import com.h3r3t1c.wearunitconverter.ui.compose.common.rememberResponsiveColumnPadding
import com.h3r3t1c.wearunitconverter.util.ConverterType
import com.h3r3t1c.wearunitconverter.util.TypeUnit


@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun UnitPickerDialog(visible: Boolean, currentUnit: TypeUnit, type: ConverterType, onDismiss: () -> Unit, onUnitPick: (TypeUnit) -> Unit){
    Dialog(
        visible = visible,
        onDismissRequest = onDismiss
    ) {

        val context = LocalContext.current
        val padding = rememberResponsiveColumnPadding(
            first = ColumnItemType.ListHeader,
            last = ColumnItemType.Button
        )
        val listState = rememberScalingLazyListState(
            0
        )
        ScreenScaffold(
            scrollState = listState,
            contentPadding = padding,

        ) {
            ScalingLazyColumn(
                state = listState,
                contentPadding = padding,
                autoCentering = null,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                item("header") {
                    ListHeader {
                        Text(ConverterType.toDisplayName(context, type), textAlign = TextAlign.Center)
                    }
                }
                items(type.units.size) {
                    val unit = type.units[it]
                    Option(unit, unit == currentUnit) {
                        onUnitPick(unit)
                    }
                }
            }
        }
    }
}
@Composable
fun Option(unit: TypeUnit, selected: Boolean, onClick: () -> Unit){
    RadioButton(
        selected = selected,
        onSelect = onClick,
        secondaryLabel = {
            Text(TypeUnit.unitToString(unit))
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(TypeUnit.unitToString(unit, true))
    }
}