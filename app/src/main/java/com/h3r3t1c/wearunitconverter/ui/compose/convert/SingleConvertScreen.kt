package com.h3r3t1c.wearunitconverter.ui.compose.convert

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.h3r3t1c.wearunitconverter.util.ConvertHelper

@Composable
fun SingleConvertScreen(viewModel: ConvertViewModel){
    ScreenScaffold(
        timeText = {}
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UnitButton(viewModel.topUnitString) {
                viewModel.dialogState = ConvertDialogState.CHANGE_FIRST_UNIT
            }
            Value(viewModel.topValue, viewModel.maxSigDigits){
                viewModel.dialogState = ConvertDialogState.CHANGE_FIRST_VALUE
            }
            Text("=")
            Value(viewModel.bottomValue, viewModel.maxSigDigits){
                viewModel.dialogState = ConvertDialogState.CHANGE_SECOND_VALUE
            }
            UnitButton(viewModel.bottomUnitString) {
                viewModel.dialogState = ConvertDialogState.CHANGE_SECOND_UNIT
            }
        }
    }
}
@Composable
private fun UnitButton(text: String, onClick: () -> Unit){
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(48.dp))
            .clip(RoundedCornerShape(48.dp))
            .height(48.dp).clickable{
                onClick()
            },
        contentAlignment = Alignment.Center
    ){
        Text(text, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleMedium)
    }
}
@Composable
private fun ColumnScope.Value(value: Double, maxDigits: Int, onClick: () -> Unit){
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .weight(1f)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ){
        BasicText(
            text = ConvertHelper.formatNumber(maxDigits, value),
            maxLines = 1,
            style = TextStyle(
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            autoSize = TextAutoSize.StepBased(TextUnit(12f, TextUnitType.Sp), TextUnit(24f, TextUnitType.Sp))
        )
    }
}