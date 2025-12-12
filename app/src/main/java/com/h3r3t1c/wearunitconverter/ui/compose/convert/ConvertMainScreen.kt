package com.h3r3t1c.wearunitconverter.ui.compose.convert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.pager.HorizontalPager
import androidx.wear.compose.foundation.pager.rememberPagerState
import androidx.wear.compose.material3.AnimatedPage
import androidx.wear.compose.material3.HorizontalPageIndicator
import androidx.wear.compose.material3.HorizontalPagerScaffold
import androidx.wear.compose.material3.PagerScaffoldDefaults
import com.h3r3t1c.wearunitconverter.ui.compose.dialogs.NumberInputDialog
import com.h3r3t1c.wearunitconverter.ui.compose.dialogs.UnitPickerDialog
import com.h3r3t1c.wearunitconverter.util.ConvertHelper

@Composable
fun ConvertMainScreen(navController: NavHostController, type: String, number: String, firstUnit: Int, secondUnit: Int){
    val context = LocalContext.current
    val viewModel = viewModel<ConvertViewModel>(factory = ConvertViewModel.getFactory(context, type, number, firstUnit, secondUnit), key = type)
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { 2 }

    HorizontalPagerScaffold(
        pagerState = pagerState,
        pageIndicator = {
            HorizontalPageIndicator(pagerState)
        },
    ) {
        HorizontalPager(
            state = pagerState,
            flingBehavior = PagerScaffoldDefaults.snapWithSpringFlingBehavior(state = pagerState),
            beyondViewportPageCount = 0,
            rotaryScrollableBehavior = null,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)

        ) { page ->
            AnimatedPage(pageIndex = page, pagerState = pagerState) {
                when (page) {
                    0 -> SingleConvertScreen(viewModel)
                    1 -> AllConversionsScreen(viewModel)
                }
            }
        }
    }
    Dialogs(viewModel)
}
@Composable
private fun Dialogs(viewModel: ConvertViewModel){
    val context = LocalContext.current
    UnitPickerDialog(
        viewModel.dialogState == ConvertDialogState.CHANGE_FIRST_UNIT,
        viewModel.firstUnit,
        viewModel.type,
        { viewModel.dialogState = ConvertDialogState.NONE }
    ) {
        viewModel.updateFirstUnit(it)
    }

    UnitPickerDialog(
        viewModel.dialogState == ConvertDialogState.CHANGE_SECOND_UNIT,
        viewModel.secondUnit,
        viewModel.type,
        { viewModel.dialogState = ConvertDialogState.NONE }
    ){
        viewModel.updateSecondUnit(it)
    }

    NumberInputDialog(
        viewModel.dialogState == ConvertDialogState.CHANGE_FIRST_VALUE,
        ConvertHelper.formatNumber(context, viewModel.topValue),
        {viewModel.dialogState = ConvertDialogState.NONE}
    ){
        viewModel.updateFirstValue(it.toDouble())
    }

    NumberInputDialog(
        viewModel.dialogState == ConvertDialogState.CHANGE_SECOND_VALUE,
        ConvertHelper.formatNumber(context, viewModel.bottomValue),
        {viewModel.dialogState = ConvertDialogState.NONE}
    ){
        viewModel.updateSecondValue(it.toDouble())
    }
}
