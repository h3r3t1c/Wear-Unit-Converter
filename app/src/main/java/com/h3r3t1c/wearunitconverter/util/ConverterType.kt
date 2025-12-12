package com.h3r3t1c.wearunitconverter.util

import android.content.Context
import androidx.compose.runtime.Stable
import com.h3r3t1c.wearunitconverter.R

enum class ConverterType {
    TEMPERATURE,
    SPEED,
    TIME,
    LENGTH,
    WEIGHT;

    companion object{
        @Stable
        fun toDisplayName(context: Context, type: ConverterType): String{
            return when(type){
                TEMPERATURE -> context.getString(R.string.temperature)
                SPEED -> context.getString(R.string.speed)
                TIME -> context.getString(R.string.time)
                LENGTH -> context.getString(R.string.length)
                WEIGHT -> context.getString(R.string.weight)
            }
        }
    }
}