package com.h3r3t1c.wearunitconverter.demo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.CurvedModifier
import androidx.wear.compose.foundation.CurvedTextStyle
import androidx.wear.compose.foundation.angularSize
import androidx.wear.compose.foundation.background
import androidx.wear.compose.foundation.basicCurvedText
import androidx.wear.compose.foundation.padding
import androidx.wear.compose.foundation.parentDataModifier
import androidx.wear.compose.foundation.radialSize
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import com.h3r3t1c.wearunitconverter.util.UnitType

@Composable
fun crvText(){
    Scaffold(
        timeText = {
            CurvedLayout(modifier = Modifier.fillMaxWidth().padding(top=4.dp)){
                basicCurvedText(
                    "100 *f",
                    CurvedModifier.background(color = Color(0xff2196F3), StrokeCap.Round).padding(4.dp),
                    overflow = TextOverflow.Ellipsis,
                    style = {
                        CurvedTextStyle(
                            fontSize = 16.sp,
                            color = Color.White,

                        )
                    }
                )
            }
        }
    ){

    }

}

@Preview()
@Composable
fun test(){
crvText()
}