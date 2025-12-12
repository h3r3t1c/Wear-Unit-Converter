package com.h3r3t1c.wearunitconverter.ui.compose.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.InlineSlider
import androidx.wear.compose.material.InlineSliderDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Dialog
import com.h3r3t1c.wearunitconverter.util.AppPrefs

@Composable
fun DecimalLengthDialog(startingValue:Int, onDismiss:(selectedVal:Int)->Unit){

    val context = LocalContext.current

    var showDialog by remember {
        mutableStateOf(true)
    }
    var value by remember {
        mutableIntStateOf(startingValue)
    }
    Dialog(showDialog = showDialog,
        onDismissRequest = {
            showDialog = false
            onDismiss(value)
            AppPrefs.setMaxSigDigits(context, value)
        },
        content ={
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Icon(imageVector = Icons.Default.Settings, contentDescription = null, tint = Color.Black, modifier = Modifier.height(32.dp).width(32.dp))
                Text(
                    text = "Decimal Length",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                InlineSlider(
                    value = value,
                    onValueChange = { value = it },
                    increaseIcon = { Icon(InlineSliderDefaults.Increase, "Increase") },
                    decreaseIcon = { Icon(InlineSliderDefaults.Decrease, "Decrease") },
                    valueProgression = 0..8,
                    segmented = true,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                )
                Text(
                    text = value.toString(),
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }

    )
}