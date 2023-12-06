package com.h3r3t1c.wearunitconverter.ext

import android.util.Log
import androidx.wear.compose.foundation.lazy.ScalingLazyListState


val ScalingLazyListState.topAlpha: Float
    get() = if(centerItemIndex <2){
        // this will be improved at some point to fade in as scrolled
        /*val totalItems = layoutInfo.totalItemsCount

        val itemLengthInPx = layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
        val totalLengthInPx = totalItems * itemLengthInPx
        if(totalLengthInPx == 0)
            0f
        else {
            val scrollPos = (centerItemIndex * itemLengthInPx) / totalLengthInPx
            0.5f
        }*/
        0.0f
    }else{ 1.0f}
