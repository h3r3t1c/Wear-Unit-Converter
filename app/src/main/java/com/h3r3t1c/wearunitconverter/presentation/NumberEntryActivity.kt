/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.h3r3t1c.wearunitconverter.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily.Companion.SansSerif
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.h3r3t1c.wearunitconverter.presentation.composables.AutoSizeText
import com.h3r3t1c.wearunitconverter.presentation.theme.WearUnitConverterTheme
import com.h3r3t1c.wearunitconverter.viewmodels.NumberInputViewModel

class NumberEntryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val viewModel:NumberInputViewModel = viewModel(factory = NumberInputViewModel.provideFactory(
                intent?.getStringExtra("value")!!, this))
            NumberEntry(this, viewModel)
        }
    }
}

@Composable
fun NumberEntry(activity: Activity?,viewModel: NumberInputViewModel) {
    WearUnitConverterTheme {

        val value = viewModel.value.collectAsState().value

        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ){
                    Column(modifier = Modifier.weight(1f)) {
                        /*Text(
                            text = value,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start=24.dp)
                        )*/
                        AutoSizeText(
                            text = value,
                            maxLines = 2,
                            maxTextSize = 18.sp,
                            minTextSize = 10.sp,
                            modifier = Modifier.padding(start=24.dp, end = 24.dp)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Enter",
                        tint = Color.Green,
                        modifier =
                        Modifier
                            .padding(end = 32.dp)
                            .clickable {
                                val intent = Intent().apply {
                                    putExtra("value", value)
                                    putExtra("loc", activity?.intent?.getStringExtra("loc"))
                                }
                                activity?.setResult(Activity.RESULT_OK, intent)
                                activity?.finish()
                            }
                    )
                }

                Spacer(modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxWidth()
                    .height(1.dp))
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.padding(start=12.dp,end=12.dp, top=4.dp, bottom = 16.dp),
                userScrollEnabled = false
            ) {
                ("1 2 3 back 4 5 6 CE 7 8 9 .").split(" ").forEach { key ->
                    item {
                        CVNumberKey(key, viewModel::updateValue)
                    }
                }
                item{

                }
                item() {
                    CVNumberKey("0", viewModel::updateValue)
                }
                item{
                    CVNumberKey(key = "-", viewModel::updateValue)
                }
            }
        }
    }
}
@Composable
fun CVNumberKey(
    key: String,
    keyEvent : (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { keyEvent(key) },
        contentAlignment = Alignment.Center
    ) {
        when (key) {
            "back" -> Icon(
                Icons.Default.KeyboardBackspace,
                "back",
                tint = Color.White
            )
            else -> Text(key, fontFamily = SansSerif, fontSize = 16.sp)
        }
    }
}