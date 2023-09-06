package com.h3r3t1c.wearunitconverter.ext

import android.util.Log
import androidx.wear.compose.material.ScalingLazyListState

val ScalingLazyListState.topAlpha: Float
    get() = if(centerItemIndex <2){
        0.0f
    }else{ 1.0f}
