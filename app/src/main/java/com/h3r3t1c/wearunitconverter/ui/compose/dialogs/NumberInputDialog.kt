package com.h3r3t1c.wearunitconverter.ui.compose.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Dialog
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.ui.WearStyleHelper
import com.h3r3t1c.wearunitconverter.ui.compose.common.FontScaleIndependent
import com.h3r3t1c.wearunitconverter.ui.compose.common.fillVerticalRectangle
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NumberInputDialog(visible: Boolean, initialNumber: String = "0", onDismiss: () -> Unit, onConfirm: (String) -> Unit){
    Dialog(
        visible = visible,
        onDismissRequest = onDismiss
    ) {
        val coroutineScope = rememberCoroutineScope()
        var text by remember(initialNumber) { mutableStateOf(if(initialNumber.endsWith(".0")) initialNumber.dropLast(2) else initialNumber) }
        val vibrate = LocalHapticFeedback.current
        val scrollState = rememberScrollState()
        var textSize by remember { mutableStateOf(18f) }
        val updateText: (String) -> Unit = { key ->
            if(key == "ok"){
                vibrate.performHapticFeedback(HapticFeedbackType.Confirm)
                onConfirm(text)
            }else {

                text = when (key) {
                    "C" -> {
                        vibrate.performHapticFeedback(HapticFeedbackType.LongPress)
                        textSize = 18f
                        "0"
                    }
                    "back" -> {
                        vibrate.performHapticFeedback(HapticFeedbackType.VirtualKey)
                        if (text.isNotEmpty()) {
                            text = text.dropLast(1)
                            if (text == "-" || text.isEmpty() || text == "-0")
                                "0"
                            else text
                        } else "0"
                    }

                    "." -> {
                        vibrate.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        if (text.isEmpty()) "0." else if (text.contains(".")) text else text + key
                    }
                    "-" -> {
                        vibrate.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        if (text.startsWith("-")) text.drop(1) else key + text
                    }
                    else -> {
                        vibrate.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        if (text.startsWith("0.") || text.startsWith("-0.")) text + key
                        else if (text == "0")
                            key
                        else text + key
                    }
                }
                coroutineScope.launch {
                    delay(100)
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            }
        }

        LaunchedEffect(initialNumber) {
            coroutineScope.launch {
                awaitFrame()
                delay(200)
                scrollState.scrollTo(scrollState.maxValue)
            }
        }
        val isLargeScreen = WearStyleHelper.isLargeScreen()

        ScreenScaffold(
            timeText = { },
            modifier = Modifier.fillMaxSize()
        ) {
            FontScaleIndependent {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //Spacer(Modifier.weight(1f))

                    Row(
                        modifier = Modifier
                            .fillVerticalRectangle()
                            .padding(horizontal = if(isLargeScreen) 4.dp else 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicText(
                            text = text,
                            modifier = Modifier
                                .weight(1f) then if(textSize == 12f) Modifier.horizontalScroll(scrollState) else Modifier,
                            style = TextStyle(
                                color = Color.White,
                                textAlign = TextAlign.Right,
                                fontSize = if(textSize == 12f) TextUnit(textSize, TextUnitType.Sp) else TextUnit.Unspecified
                            ),
                            onTextLayout = {
                                textSize = it.layoutInput.style.fontSize.value
                            },
                            maxLines = 1,
                            autoSize = if(textSize > 12) TextAutoSize.StepBased(TextUnit(12f, TextUnitType.Sp), TextUnit(18f, TextUnitType.Sp)) else null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_backspace),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable { updateText("back") }
                                .padding(8.dp)
                        )
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        userScrollEnabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)

                    ) {
                        ("1 2 3 C 4 5 6 - 7 8 9 ok * 0 .").split(" ").forEach { key ->
                            item(key = key) {
                                NumberKey(key) {
                                    updateText(it)
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
@Composable
private fun NumberKey(key: String, keyEvent: (String) -> Unit){
    val isLargeScreen = WearStyleHelper.isLargeScreen()
    when(key){
        "ok" ->{
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier

                    .size(if(isLargeScreen) 34.dp else 28.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .clip(CircleShape)
                    .clickable { keyEvent(key) }
            )
        }

        "*", "^" ->{
            Box(modifier = Modifier.fillMaxWidth())
        }
        ".","-", "C" -> {
            Text(
                key,
                color = MaterialTheme.colorScheme.onTertiary,
                style = if(isLargeScreen) MaterialTheme.typography.numeralSmall else MaterialTheme.typography.numeralExtraSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary, CircleShape)
                    .clip(CircleShape)
                    .clickable { keyEvent(key) }
            )
        }
        else ->{
            Text(
                key,
                color = MaterialTheme.colorScheme.onSurface,
                style = if(isLargeScreen) MaterialTheme.typography.numeralSmall else  MaterialTheme.typography.numeralExtraSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer, CircleShape)
                    .clip(CircleShape)
                    .clickable { keyEvent(key) }
            )
        }
    }
}