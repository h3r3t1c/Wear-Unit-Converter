package com.h3r3t1c.wearunitconverter.ui.compose.favorites

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
import androidx.compose.ui.res.vectorResource
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Dialog
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.database.data.FavoriteConversion
import com.h3r3t1c.wearunitconverter.ui.compose.common.ColumnItemType
import com.h3r3t1c.wearunitconverter.ui.compose.common.rememberResponsiveColumnPadding
import com.h3r3t1c.wearunitconverter.ui.compose.dialogs.UnitPickerDialog
import com.h3r3t1c.wearunitconverter.util.ConverterType
import com.h3r3t1c.wearunitconverter.util.TypeUnit

@Composable
fun AddFavoriteDialog(visible: Boolean, onDismiss: () -> Unit, onAdd: (FavoriteConversion) -> Unit){
    Dialog(
        visible = visible,
        onDismissRequest = onDismiss
    ) {
        var type by remember { mutableStateOf(ConverterType.TEMPERATURE) }
        var from by remember { mutableStateOf(ConverterType.TEMPERATURE.units[0]) }
        var to by remember { mutableStateOf(ConverterType.TEMPERATURE.units[1]) }

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
            edgeButton = {
                EdgeButton(onClick = {
                    onAdd(FavoriteConversion(type = type, from = from, to = to))
                }){
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                        contentDescription = null,
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
                        Text("Add Favorite")
                    }
                }
                item{
                    Type(type){
                        type = it
                        from = it.units[0]
                        to = it.units[1]
                    }
                }
                item{
                    Unit("From", from, type){
                        if(to == it)
                            to = from
                        from = it
                    }
                }
                item{
                    Unit("To", to, type){
                        if(from == it)
                            from = to
                        to = it
                    }
                }
            }
        }
    }
}
@Composable
private fun Type(type: ConverterType, onChange: (ConverterType) -> Unit){
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    Button(
        onClick = { showDialog = true },
        label = {
            Text("Type", style = MaterialTheme.typography.titleMedium)
        },
        secondaryLabel = {
            Text(text = ConverterType.toDisplayName(context, type))
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.filledTonalButtonColors()
    )
    TypePickerDialog(showDialog, onDismiss = { showDialog = false }){
        onChange(it)
        showDialog = false
    }
}
@Composable
private fun Unit(title: String, unit: TypeUnit, type: ConverterType, onChange: (TypeUnit) -> Unit){
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    Button(
        onClick = { showDialog = true },
        label = {
            Text(title, style = MaterialTheme.typography.titleMedium)
        },
        secondaryLabel = {
            Text(text = TypeUnit.unitToString(unit, true))
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.filledTonalButtonColors()
    )
    UnitPickerDialog(showDialog, unit, type, onDismiss = { showDialog = false }){
        showDialog = false
        onChange(it)
    }
}