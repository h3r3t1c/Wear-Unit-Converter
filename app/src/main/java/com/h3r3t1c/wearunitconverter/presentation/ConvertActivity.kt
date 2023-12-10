
package com.h3r3t1c.wearunitconverter.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalContext
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
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.background
import androidx.wear.compose.foundation.basicCurvedText
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListAnchorType
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.padding
import androidx.wear.compose.foundation.rememberActiveFocusRequester


import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold


import androidx.wear.compose.material.Text


import com.h3r3t1c.wearunitconverter.dialogs.CreateUnitPickerDialog
import com.h3r3t1c.wearunitconverter.ext.topAlpha
import com.h3r3t1c.wearunitconverter.presentation.composables.AutoSizeText
import com.h3r3t1c.wearunitconverter.presentation.theme.Blue500
import com.h3r3t1c.wearunitconverter.presentation.theme.Red500
import com.h3r3t1c.wearunitconverter.presentation.theme.WearUnitConverterTheme
import com.h3r3t1c.wearunitconverter.util.ConvertHelper
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

const val NUMBER_FONT_SIZE = 18
lateinit var unitsList:Array<Int>

class ConvertActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = this.intent.getIntExtra("type",0)
        unitsList = UnitType.getUnitTypeList(type)
        setContent {
            val viewModel:ConvertActivityViewModel = viewModel(factory = ConvertActivityViewModel.provideFactory(
                 unitsList[0], unitsList[1], this, unitsList, this))
            ConvertWearApp(viewModel)
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConvertWearApp(viewModel: ConvertActivityViewModel) {
    WearUnitConverterTheme {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) {
            2 // is the number of pages...
        }
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
                modifier = Modifier.padding(bottom = 2.dp, start = 2.dp),
                selectedColor = if(pagerState.currentPage == 0) Color.Black else Color.White
            ) }
        ) {
            HorizontalPager(state = pagerState) { page ->
                if(page == 0)
                    PageOne(viewModel)
                else
                    PageTwo(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun PageTwo(viewModel: ConvertActivityViewModel){
    val context = LocalContext.current
    val isRound = context.resources.configuration.isScreenRound

    val fromUnit = viewModel.topUnit.collectAsState().value

    val textValueTop = viewModel.topText.collectAsState().value

    var showPickerDialog by remember{
        mutableStateOf(false)
    }
    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberScalingLazyListState()
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if(intent?.getStringExtra("loc").equals("top")) {
                    viewModel.updateTopText(context, intent?.getStringExtra("value")!!)
                }

            }
        }
    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
        timeText = {
            if(isRound) {
                CurvedLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(listState.topAlpha)
                        .padding(top = 4.dp)
                ) {
                    basicCurvedText(
                        textValueTop + " " + UnitType.unitTypeToString(fromUnit),
                        CurvedModifier.background(color = Red500, StrokeCap.Round).padding(0.dp),
                        overflow = TextOverflow.Ellipsis,
                        style = {
                            CurvedTextStyle(
                                fontSize = 14.sp,
                                color = Color.White,

                                )
                        }
                    )
                }
            }
            else{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(listState.topAlpha),
                    contentAlignment = Alignment.Center
                ){

                    Box(modifier = Modifier
                        .padding(4.dp)
                        .background(color = Red500, shape = MaterialTheme.shapes.large)){
                        Text(
                            text = textValueTop + " " + UnitType.unitTypeToString(fromUnit),
                            color = Color.White,
                            fontSize = 16.sp,
                            maxLines = 2,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 2.dp, bottom = 2.dp, start = 6.dp, end = 6.dp)

                        )
                    }
                }
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
                        listState.animateScrollBy(0f)
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
                        .padding(top = 8.dp, start = 4.dp, end = 4.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colors.surface,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 40.dp)
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
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(end = 1.dp, top = 1.dp, bottom = 1.dp)
                            .clickable {

                                showPickerDialog = true
                            }
                            .background(
                                color = Red500,
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
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            item{
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp), contentAlignment = Alignment.Center){
                    Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = "", tint = Color.White)
                }
            }

            items(viewModel.activeUnitsList.size){ index->
                ConversionEntry(s = textValueTop, fromUnit = viewModel.activeUnitsList[index], toUnit = fromUnit)
            }

        }

        if(showPickerDialog) {
            CreateUnitPickerDialog(unitsList){value ->
                showPickerDialog = false
                if (value != -1) {
                    viewModel.updateTopUnit(context, value)
                }
            }
        }
        LaunchedEffect(Unit){
            listState.scrollToItem(0)
            focusRequester.requestFocus()
        }
    }
}
@Composable
fun ConversionEntry(s:String, fromUnit:Int, toUnit:Int){
    val context = LocalContext.current
    var showFull by remember { // this state is lost while scrolling but that's fine for now...
        mutableStateOf(false)
    }
    val txt = remember(s, fromUnit, toUnit, showFull) {
        "${ConvertHelper.convertUnits(context, s.toDouble(), toUnit, fromUnit)} ${UnitType.unitTypeToString(fromUnit, showFull)}"
    }
    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xff212121), shape = RoundedCornerShape(16.dp))
            .clickable {
                showFull = !showFull
            },
    ) {
        Text(
            text = txt,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun PageOne(viewModel: ConvertActivityViewModel){
    val context = LocalContext.current
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if(intent?.getStringExtra("loc").equals("top")) {
                    viewModel.updateTopText(context, intent?.getStringExtra("value")!!)
                }
                else{
                    viewModel.updateBottomText(context, intent?.getStringExtra("value")!!)
                }
            }
        }

    Scaffold(

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)

        ) {
            ConversionEntryTop(viewModel, Modifier.weight(1f), startForResult)
            Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Color(0xff333333)))
            ConversionEntryBottom(viewModel, Modifier.weight(1f), startForResult)
        }
    }
}
@Composable
fun ConversionEntryTop(viewModel: ConvertActivityViewModel, modifier: Modifier, startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>){
    val context = LocalContext.current
    val unit = viewModel.topUnit.collectAsState().value
    val text = viewModel.topText.collectAsState().value
    var showUnitPickerDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .clickable {
                    showUnitPickerDialog = true
                }
                .background(
                    color = Red500,
                ),
            contentAlignment = Alignment.Center,

        ){
            Text(
                modifier = Modifier,
                text = UnitType.unitTypeToString(unit),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    val intent: Intent =
                        Intent(context, NumberEntryActivity::class.java).apply {
                            putExtra("value", text)
                            putExtra("loc", "top")
                        }
                    startForResult.launch(intent)
                },
            contentAlignment = Alignment.CenterStart
        ){

            AutoSizeText(
                text = text,
                color = Color.White,
                maxLines = 2,
                alignment = Alignment.Center,
                minTextSize = 12.sp,
                maxTextSize = 24.sp,
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }
    }
    if(showUnitPickerDialog) {
        CreateUnitPickerDialog(unitsList){value ->
            showUnitPickerDialog = false
            if (value != -1) {
                viewModel.updateTopUnit(context, value)
            }
        }
    }
}
@Composable
fun ConversionEntryBottom(viewModel: ConvertActivityViewModel, modifier: Modifier, startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>){
    val context = LocalContext.current
    val unit = viewModel.bottomUnit.collectAsState().value
    val text = viewModel.bottomText.collectAsState().value
    var showUnitPickerDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    val intent: Intent =
                        Intent(context, NumberEntryActivity::class.java).apply {
                            putExtra("value", text)
                            putExtra("loc", "bottom")
                        }
                    startForResult.launch(intent)
                },
            contentAlignment = Alignment.CenterStart
        ){
            AutoSizeText(
                text = text,
                color = Color.White,
                maxLines = 2,
                alignment = Alignment.Center,
                minTextSize = 12.sp,
                maxTextSize = 24.sp,
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .clickable {
                    showUnitPickerDialog = true
                }
                .background(
                    color = Red500,
                ),
            contentAlignment = Alignment.Center,

            ){
            Text(
                modifier = Modifier,
                text = UnitType.unitTypeToString(unit),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
    if(showUnitPickerDialog) {
        CreateUnitPickerDialog(unitsList){value ->
            showUnitPickerDialog = false
            if (value != -1) {
                viewModel.updateBottomUnit(context, value)
            }
        }
    }
}

