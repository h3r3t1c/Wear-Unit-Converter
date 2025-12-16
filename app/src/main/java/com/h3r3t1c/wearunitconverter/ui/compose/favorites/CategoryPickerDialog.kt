package com.h3r3t1c.wearunitconverter.ui.compose.favorites

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
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Dialog
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.ui.compose.common.ColumnItemType
import com.h3r3t1c.wearunitconverter.ui.compose.common.rememberResponsiveColumnPadding
import com.h3r3t1c.wearunitconverter.util.CategoryHelper
import eu.hansolo.unit.converter.Converter

@Composable
fun CategoryPickerDialog(visible: Boolean, onDismiss: () -> Unit, onSelect: (Converter.Category) -> Unit){
    val context = LocalContext.current
    Dialog(
        visible = visible,
        onDismissRequest = onDismiss
    ) {
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
                        Text(stringResource(R.string.type))
                    }
                }
                items(Converter.Category.entries.sortedBy { CategoryHelper.getDisplayName(context, it) }, key = { it.name }){
                    Button(
                        onClick = {
                            onSelect(it)

                        },
                        label = {
                            Text(CategoryHelper.getDisplayName(context, it), style = MaterialTheme.typography.titleMedium)
                        },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(CategoryHelper.getIconForCategory(it)),
                                contentDescription = null,
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.filledTonalButtonColors()
                    )
                }
            }
        }
    }
}