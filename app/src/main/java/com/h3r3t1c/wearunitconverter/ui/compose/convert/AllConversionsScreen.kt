package com.h3r3t1c.wearunitconverter.ui.compose.convert

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.CheckboxButtonDefaults
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.h3r3t1c.wearunitconverter.ui.compose.common.ColumnItemType
import com.h3r3t1c.wearunitconverter.ui.compose.common.rememberResponsiveColumnPadding
import eu.hansolo.unit.converter.Converter

@Composable
fun AllConversionsScreen(viewModel: ConvertViewModel){
    val context = LocalContext.current
    val padding = rememberResponsiveColumnPadding(
        first = ColumnItemType.Button,
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
            item{
                SplitButton(viewModel.firstValue, viewModel.firstUnit.UNIT.unitShort, { viewModel.dialogState = ConvertDialogState.CHANGE_FIRST_VALUE }){
                    viewModel.dialogState = ConvertDialogState.CHANGE_FIRST_UNIT
                }
            }
            items(viewModel.conversions){
                Conversion(it.first, it.second)
            }
        }
    }
}
@Composable
private fun SplitButton(text: String, unit: String, onPrimaryClick: () -> Unit, onSecondaryClick: () -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier.fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp)
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Max)
                .clip(CheckboxButtonDefaults.splitCheckboxButtonShape),
    ) {
        Row(
            modifier =
                Modifier
                    .clickable(onClick = onPrimaryClick,)

                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.0.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(CheckboxButtonDefaults.ContentPadding)
                    .weight(1.0f),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.size(2.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .clickable(onClick = onSecondaryClick)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.0.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .defaultMinSize(minWidth = 48.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .padding(horizontal = 2.dp),
        ) {
            Text(text = unit, maxLines = 1, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
private fun Conversion(text: String, unit: Converter.UnitDefinition){
    var showFull by remember { mutableStateOf(false) }
    Button(
        onClick = {
            showFull = !showFull
        },
        label = {
            Text(text = text+" ${unit.UNIT.unitShort}", maxLines = if(showFull) Int.MAX_VALUE else 1, overflow = TextOverflow.Ellipsis)
        },
        secondaryLabel = {
            Text(text = unit.UNIT.unitName)
        },
        modifier = Modifier.fillMaxSize(),
        colors = ButtonDefaults.filledTonalButtonColors()
    )
}