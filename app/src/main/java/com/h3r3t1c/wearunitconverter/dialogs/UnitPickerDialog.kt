package com.h3r3t1c.wearunitconverter.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold

import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Dialog
import com.h3r3t1c.wearunitconverter.presentation.theme.Red500

import com.h3r3t1c.wearunitconverter.util.UnitType
import kotlinx.coroutines.launch


@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun CreateUnitPickerDialog(items:Array<Int>, onUnitPick:(selectedUnit:Int)->Unit){

    var showDialog by remember{
        mutableStateOf(true)
    }
    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberScalingLazyListState()
    Dialog(showDialog = showDialog,
        onDismissRequest = {
            showDialog = false
            onUnitPick(-1)
        },
        content ={
            Scaffold(
                positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
            ) {
                ScalingLazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .onRotaryScrollEvent {
                        coroutineScope.launch {
                            listState.scrollBy(it.verticalScrollPixels)
                            listState.animateScrollBy(0f)
                        }
                        true
                    }
                    .focusRequester(focusRequester)
                    .focusable(),
                    state = listState

                ){

                    item {
                        ListHeader {
                            Text(text = "Select Unit",color = Red500)
                        }
                    }
                    items(items.size){ index->
                        CreateOption(i = items[index]){
                            showDialog = false
                            onUnitPick(it)
                        }
                    }
                }
            }
            LaunchedEffect(Unit){
                focusRequester.requestFocus()
            }
        }

    ) 
}
@Composable
fun CreateOption(i:Int, onUnitPick: (selectedUnit: Int) -> Unit){
    Card(onClick = { onUnitPick(i) }, modifier = Modifier.fillMaxWidth()) {
        Text(text = UnitType.unitTypeToString(i, true), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 18.sp)
    }
}