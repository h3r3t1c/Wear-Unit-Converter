
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
import androidx.compose.foundation.gestures.scrollBy
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
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Web
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.remote.interactions.RemoteActivityHelper
import androidx.wear.widget.ConfirmationOverlay
import com.h3r3t1c.wearunitconverter.BuildConfig
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.ext.findActivity
import com.h3r3t1c.wearunitconverter.presentation.theme.WearUnitConverterTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            ) }
        ) {
            HorizontalPager(state = pagerState) { page ->
                if(page == 0)
                    HomePage()
                else
                    AboutPage()
            }
        }
    }
}
@Composable
fun AboutPage(){
    val listState = rememberScalingLazyListState()
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

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
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            state = listState
        ) {
            item {
                ListHeader {
                    Text(text = stringResource(R.string.about))
                }
            }
            item{
                AboutOption(title = stringResource(R.string.created_by), msg = "Thomas Otero", ico = Icons.Default.Person, null)
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
                AboutOption(title = stringResource(R.string.version), msg = BuildConfig.VERSION_NAME+" ("+BuildConfig.VERSION_CODE+")", ico = Icons.Default.Info, null)
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
            .setDuration(500)
            .showOn(context.findActivity())
        val remoteActivityHelper = RemoteActivityHelper(context)
        remoteActivityHelper.startRemoteActivity(
            Intent(Intent.ACTION_VIEW).setData(
                Uri.parse(url)
            ).addCategory(Intent.CATEGORY_BROWSABLE), null
        )
}
@Composable
fun HomePage(){
    val listState = rememberScalingLazyListState()
    val focusRequester = remember { FocusRequester() }
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
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            state = listState
        ) {
            item {
                ListHeader {
                    Text(text = stringResource(R.string.unit_conversion))
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
fun AboutOption(title:String, msg:String?, ico: ImageVector, click: (() -> Unit)?){
    Chip(
        label = {
            Text(text = title)
        },
        secondaryLabel = {
            if(msg != null)
                Text(text = msg)
        },
        onClick = {
            if (click != null) {
                click()
            }
        },
        icon = {
            Icon(imageVector = ico, contentDescription = "")
        },
        colors = ChipDefaults.primaryChipColors(
            contentColor = Color.White,
            backgroundColor = MaterialTheme.colors.surface
        ),
        modifier = Modifier.fillMaxWidth()
    )
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
    WearApp()
}