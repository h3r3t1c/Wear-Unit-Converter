/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.h3r3t1c.wearunitconverter.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.CurvedModifier
import androidx.wear.compose.foundation.CurvedTextStyle
import androidx.wear.compose.foundation.background
import androidx.wear.compose.foundation.basicCurvedText
import androidx.wear.compose.foundation.curvedComposable
import androidx.wear.compose.foundation.padding
import androidx.wear.compose.material.AutoCenteringParams


import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListAnchorType
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberScalingLazyListState
import com.h3r3t1c.wearunitconverter.dialogs.UnitPickerListener
import com.h3r3t1c.wearunitconverter.dialogs.createUnitPickerDialog
import com.h3r3t1c.wearunitconverter.ext.topAlpha
import com.h3r3t1c.wearunitconverter.presentation.ConverterType.TYPE_TIME
import com.h3r3t1c.wearunitconverter.presentation.theme.Blue500
import com.h3r3t1c.wearunitconverter.presentation.theme.WearUnitConverterTheme
import com.h3r3t1c.wearunitconverter.util.Converter
import com.h3r3t1c.wearunitconverter.util.UnitType
import com.h3r3t1c.wearunitconverter.viewmodels.ConvertActivityViewModel
import kotlinx.coroutines.launch

object ConverterType{
    const val TYPE_TEMPERATURE = 0
    const val TYPE_SPEED = 1
    const val TYPE_TIME = 2
    const val TYPE_LENGTH = 3
    const val TYPE_WEIGHT = 4
}

lateinit var unitPickerListener:UnitPickerListener
lateinit var unitsList:Array<Int>

class ConvertActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = this.intent.getIntExtra("type",0)
        unitsList = UnitType.getUnitTypeList(type)
        setContent {
            val viewModel:ConvertActivityViewModel = viewModel(factory = ConvertActivityViewModel.provideFactory(
                unitsList[0], unitsList[1], unitsList, this))
            ConvertWearApp(type, viewModel)
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConvertWearApp(type:Int, viewModel: ConvertActivityViewModel) {
    WearUnitConverterTheme {
        val pagerState = rememberPagerState()
        val pageIndicatorState: PageIndicatorState = remember {
            object : PageIndicatorState {
                override val pageOffset: Float
                    get() = pagerState.currentPageOffsetFraction
                override val selectedPage: Int
                    get() = pagerState.currentPage
                override val pageCount: Int
                    get() = 2
            }
        }

        Scaffold(
            pageIndicator = { HorizontalPageIndicator(
                pageIndicatorState = pageIndicatorState,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            ) }
        ) {
            HorizontalPager(pageCount = 2, state = pagerState) { page ->
                if(page == 0)
                    PageOne(viewModel, type)
                else
                    PageTwo(viewModel, type)
            }
        }
    }
}

@Composable
fun PageTwo(viewModel: ConvertActivityViewModel, type: Int){
    val context = LocalContext.current

    val fromUnit = viewModel.topUnit.collectAsState().value

    val textValueTop = viewModel.topText.collectAsState().value

    var showPickerDialog by remember{
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberScalingLazyListState()
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if(intent?.getStringExtra("loc").equals("top")) {
                    viewModel.updateTopText(intent?.getStringExtra("value")!!)
                }

            }
        }
    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
        timeText = {
            CurvedLayout(modifier = Modifier
                .fillMaxWidth()
                .alpha(listState.topAlpha)
                .padding(top = 4.dp)

            ){
                basicCurvedText(
                    textValueTop+" "+UnitType.unitTypeToString(fromUnit),
                    CurvedModifier.background(color = Blue500, StrokeCap.Round).padding(4.dp),
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
    ) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .onRotaryScrollEvent {
                    coroutineScope.launch {
                        listState.scrollBy(it.verticalScrollPixels)
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            state = listState,
            autoCentering = AutoCenteringParams(0,0),
            anchorType = ScalingLazyListAnchorType.ItemCenter
        ) {

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colors.surface,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val intent: Intent =
                                    Intent(context, NumberEntryActivity::class.java).apply {
                                        putExtra("value", textValueTop)
                                        putExtra("loc", "top")
                                    }
                                startForResult.launch(intent)
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = textValueTop,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 1.dp, top = 1.dp, bottom = 1.dp)
                            .clickable {
                                unitPickerListener = UnitPickerListener { value ->
                                    showPickerDialog = false
                                    if (value != -1) {
                                        viewModel.updateTopUnit(value)
                                    }
                                }
                                showPickerDialog = true
                            }
                            .background(
                                color = MaterialTheme.colors.primary,
                                shape = RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomEnd = 16.dp,
                                    bottomStart = 16.dp
                                ),
                            ),
                        contentAlignment = Alignment.Center,

                        ){
                        Text(
                            modifier = Modifier.padding(top=4.dp,bottom=4.dp),
                            text = UnitType.unitTypeToString(fromUnit),
                            textAlign = TextAlign.Center,
                            fontWeight = if(type ==TYPE_TIME) FontWeight.Normal else FontWeight.Bold
                        )
                    }
                }
            }
            ////
            item{
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp), contentAlignment = Alignment.Center){
                    Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = "", tint = Color.White)
                }
            }

            ////
            items(viewModel.activeUnitsList.size){ index->
                ConversionEntry(s = textValueTop, i = viewModel.activeUnitsList[index], toUnit = fromUnit, type)
            }

        }

        if(showPickerDialog) {
            createUnitPickerDialog(showPickerDialog, unitsList, unitPickerListener)
        }
        LaunchedEffect(Unit){
            listState.scrollToItem(0)
            focusRequester.requestFocus()
        }
    }
}
@Composable
fun ConversionEntry(s:String, i:Int, toUnit:Int, type: Int){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)

            .border(
                width = 2.dp,
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(size = 16.dp)
            )

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ){
            Text(
                text = Converter.convertUnits(s.toDouble(), toUnit, i),
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 1.dp, top = 1.dp, bottom = 1.dp)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    ),
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier.padding(top=4.dp,bottom=4.dp),
                text = UnitType.unitTypeToString(i),
                textAlign = TextAlign.Center,
                fontWeight = if(type ==TYPE_TIME) FontWeight.Normal else FontWeight.Bold
            )
        }

    }
}
@Composable
fun PageOne(viewModel: ConvertActivityViewModel, type: Int){
    val context = LocalContext.current

    val fromUnit = viewModel.topUnit.collectAsState().value
    val toUnit = viewModel.bottomUnit.collectAsState().value

    val textValueTop = viewModel.topText.collectAsState().value
    val textValueBottom = viewModel.bottomText.collectAsState().value

    var showPickerDialog by remember{
        mutableStateOf(false)
    }

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if(intent?.getStringExtra("loc").equals("top")) {
                    viewModel.updateTopText(intent?.getStringExtra("value")!!)
                }
                else{
                    viewModel.updateBottomText(intent?.getStringExtra("value")!!)
                }
            }
        }
    Scaffold(

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 24.dp, start = 32.dp, end = 32.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            val intent: Intent =
                                Intent(context, NumberEntryActivity::class.java).apply {
                                    putExtra("value", textValueTop)
                                    putExtra("loc", "top")
                                }
                            startForResult.launch(intent)
                        },
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(
                        text = textValueTop,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 1.dp, top = 1.dp, bottom = 1.dp)
                        .clickable {
                            unitPickerListener = UnitPickerListener { value ->
                                showPickerDialog = false
                                if (value != -1) {
                                    viewModel.updateTopUnit(value)
                                }
                            }
                            showPickerDialog = true
                        }
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomEnd = 16.dp,
                                bottomStart = 16.dp
                            ),
                        ),
                    contentAlignment = Alignment.Center,

                    ){
                    Text(
                        modifier = Modifier,
                        text = UnitType.unitTypeToString(fromUnit),
                        textAlign = TextAlign.Center,
                        fontWeight = if(type ==TYPE_TIME) FontWeight.Normal else FontWeight.Bold
                    )
                }
            }
            ////
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), contentAlignment = Alignment.Center){
                Icon(imageVector = Icons.Default.SwapVert, contentDescription = "", tint = Color.White)
            }
            ////
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 24.dp, start = 32.dp, end = 32.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(size = 16.dp)
                    )

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            val intent: Intent =
                                Intent(context, NumberEntryActivity::class.java).apply {
                                    putExtra("value", textValueBottom)
                                    putExtra("loc", "bottom")
                                }
                            startForResult.launch(intent)
                        },
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(
                        text = textValueBottom,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 1.dp, top = 1.dp, bottom = 1.dp)
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomEnd = 16.dp,
                                bottomStart = 16.dp
                            ),
                        )
                        .clickable {
                            unitPickerListener = UnitPickerListener { value ->
                                showPickerDialog = false
                                if (value != -1) {
                                    viewModel.updateBottomUnit(value)
                                }
                            }
                            showPickerDialog = true
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        modifier = Modifier,
                        text = UnitType.unitTypeToString(toUnit),
                        textAlign = TextAlign.Center,
                        fontWeight = if(type ==TYPE_TIME) FontWeight.Normal else FontWeight.Bold
                    )
                }

            }
            if(showPickerDialog) {
                createUnitPickerDialog(showPickerDialog, unitsList, unitPickerListener)
            }
        }
    }
}
@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    //ConvertWearApp(0, null)
}
