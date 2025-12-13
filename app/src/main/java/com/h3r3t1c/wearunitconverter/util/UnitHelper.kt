package com.h3r3t1c.wearunitconverter.util

import android.icu.text.MeasureFormat
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
import androidx.compose.runtime.Stable

object UnitHelper {
    private lateinit var unitFormatter:MeasureFormat
    private lateinit var unitFormatterLong : MeasureFormat

    @Stable
    fun unitToString(unit: MeasureUnit, full:Boolean = false):String{
        if(!this::unitFormatter.isInitialized) {
            unitFormatter = MeasureFormat.getInstance(
                ULocale.getDefault(), MeasureFormat.FormatWidth.NUMERIC
            )
            unitFormatterLong = MeasureFormat.getInstance(
                ULocale.getDefault(), MeasureFormat.FormatWidth.WIDE
            )
        }
        return if(full) unitFormatterLong.getUnitDisplayName(unit) else unitFormatter.getUnitDisplayName(unit)
    }
}