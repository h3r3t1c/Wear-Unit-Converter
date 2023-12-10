
package com.h3r3t1c.wearunitconverter.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.PositionIndicator

import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text

import androidx.wear.remote.interactions.RemoteActivityHelper
import androidx.wear.widget.ConfirmationOverlay
import com.h3r3t1c.wearunitconverter.BuildConfig
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.dialogs.DecimalLengthDialog
import com.h3r3t1c.wearunitconverter.ext.findActivity
import com.h3r3t1c.wearunitconverter.presentation.theme.Blue500
import com.h3r3t1c.wearunitconverter.presentation.theme.Red500
import com.h3r3t1c.wearunitconverter.presentation.theme.WearUnitConverterTheme
import com.h3r3t1c.wearunitconverter.util.AppPrefs
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WearApp() {
    WearUnitConverterTheme {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) {
            2
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
                modifier = Modifier.padding(bottom = 4.dp)
            ) }
        ) {
            HorizontalPager(state = pagerState) { page ->
                if(page == 0)
                    HomePage()
                else
                    SettingsPage()
            }
        }
    }
}
@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun SettingsPage(){
    val listState = rememberScalingLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var sigDigits by remember {
        mutableIntStateOf(AppPrefs.getMaxSigDigits(context))
    }
    var showDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        positionIndicator = {PositionIndicator(scalingLazyListState = listState)}
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
            state = listState
        ) {
            item {
                ListHeader {
                    Text(text = stringResource(R.string.settings), color = Red500)
                }

            }
            item{
                AboutOption(true, title = "Significant Digits", msg = sigDigits.toString(), ico = Icons.Default.Settings){
                    showDialog = true
                }
            }
            item {
                ListHeader {
                    Text(text = stringResource(R.string.about), color = Red500)
                }
            }
            item{
                AboutOption(false, title = stringResource(R.string.created_by), msg = "Thomas Otero", ico = Icons.Default.Person, null)
            }
            item{
                AboutOption(title = stringResource(R.string.linkedin_profile), msg = null, ico = Icons.Default.Link) {
                    coroutineScope.launch {
                        openURL("https://www.linkedin.com/in/thomas-otero-5b8aa429/", context)
                    }
                }
            }
            item{
                AboutOption(title = stringResource(R.string.xda_profile), msg = null, ico = Icons.Default.Link) {
                    coroutineScope.launch {
                        openURL("https://forum.xda-developers.com/m/h3r3t1c.4535362/", context)
                    }
                }
            }
            item{
                AboutOption(title = stringResource(R.string.app_github), msg = null, ico = Icons.Default.Link) {
                    coroutineScope.launch {
                        openURL("https://github.com/h3r3t1c/Wear-Unit-Converter", context)
                    }
                }
            }
            item{
                AboutOption(title = stringResource(R.string.email_me), msg = null, ico = Icons.Default.Email) {
                    coroutineScope.launch {
                        openURL("mailto:th3h3r3t1c@gmail.com", context)
                    }
                }
            }
            item{
                AboutOption(false, title = stringResource(R.string.version), msg = BuildConfig.VERSION_NAME+" ("+BuildConfig.VERSION_CODE+")", ico = Icons.Default.Info, null)
            }
        }
        if(showDialog){
            DecimalLengthDialog(sigDigits) { selectedVal ->
                showDialog = false
                sigDigits = selectedVal
            }
        }
        LaunchedEffect(true){
            focusRequester.requestFocus()
        }
    }
}

fun openURL(url:String, context:Context){

        ConfirmationOverlay()
            .setType(ConfirmationOverlay.OPEN_ON_PHONE_ANIMATION)
            .setMessage("Continue on phone")
            .setDuration(2000)
            .showOn(context.findActivity())
        val remoteActivityHelper = RemoteActivityHelper(context)
        remoteActivityHelper.startRemoteActivity(
            Intent(Intent.ACTION_VIEW).setData(
                Uri.parse(url)
            ).addCategory(Intent.CATEGORY_BROWSABLE), null
        )
}
@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun HomePage(){
    val listState = rememberScalingLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        positionIndicator = {PositionIndicator(scalingLazyListState = listState)}
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
            state = listState
        ) {
            item {
                ListHeader {
                    Text(text = stringResource(R.string.unit_conversion), color = Red500)
                }
            }
            item{
                HomeOption(title = stringResource(R.string.temperature), Icons.Default.DeviceThermostat, ConverterType.TYPE_TEMPERATURE)
            }
            item{
                HomeOption(title = stringResource(R.string.speed), Icons.Default.Speed, ConverterType.TYPE_SPEED)
            }
            item{
                HomeOption(title = stringResource(R.string.length), Icons.Default.Straighten, ConverterType.TYPE_LENGTH)
            }
            item{
                HomeOption(title = stringResource(R.string.weight), Icons.Default.Scale, ConverterType.TYPE_WEIGHT)
            }
            item{
                HomeOption(title = stringResource(R.string.time), Icons.Default.Schedule, ConverterType.TYPE_TIME)
            }
        }
    }
    LaunchedEffect(true){
        focusRequester.requestFocus()
    }
}
@Composable
fun AboutOption(isEnabled:Boolean = true, title:String, msg:String?, ico: ImageVector, click: (() -> Unit)?){
    Card(
        onClick = {
            if (click != null) {
                click()
            }
        },
        enabled = isEnabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Icon(imageVector = ico, contentDescription = null, tint = Color.White)
            Column(
                modifier = Modifier.padding(start = 6.dp)
            ) {
                Text(text = title, color = Color.White)
                if(msg != null)
                    Text(text = msg, color = Blue500)
            }

        }
    }
}
@Composable
fun HomeOption(title: String, ico : ImageVector, type:Int){
    val context = LocalContext.current
     Chip(
            label = {
                  Text(text = title)
                 },
            onClick = { clickOption(context, type) },
            icon = {
                Icon(imageVector = ico, contentDescription = "")
            },
         colors = ChipDefaults.primaryChipColors(
             contentColor = MaterialTheme.colors.onSurface,
             backgroundColor = MaterialTheme.colors.surface
         ),
         modifier = Modifier.fillMaxWidth()
     )
}
fun clickOption(context: Context, type:Int){

    val intent = Intent(context, ConvertActivity::class.java).apply {
        putExtra("type", type)
    }
    context.startActivity(intent)
}
@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    
}