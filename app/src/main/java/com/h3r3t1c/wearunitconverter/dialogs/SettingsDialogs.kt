package com.h3r3t1c.wearunitconverter.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Dialog
import com.h3r3t1c.wearunitconverter.presentation.theme.Red500
import kotlinx.coroutines.launch

@Composable
fun DecimalLengthDialog(startingValue:Int, onDismiss:(selectedVal:Int)->Unit){

    var showDialog by remember {
        mutableStateOf(true)
    }
    Dialog(showDialog = showDialog,
        onDismissRequest = {
            showDialog = false
            onDismiss(1)
        },
        content ={

        }

    )
}