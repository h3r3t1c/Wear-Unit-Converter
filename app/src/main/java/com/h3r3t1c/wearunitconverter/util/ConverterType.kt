package com.h3r3t1c.wearunitconverter.util

import android.content.Context
import android.icu.util.MeasureUnit
import androidx.compose.runtime.Stable
import com.h3r3t1c.wearunitconverter.R

enum class ConverterType(val units: List<MeasureUnit>) {
    TEMPERATURE(
        listOf(
            MeasureUnit.FAHRENHEIT,
            MeasureUnit.CELSIUS,
            MeasureUnit.KELVIN
        )
    ),
    SPEED(
        listOf(
            MeasureUnit.MILE_PER_HOUR,
            MeasureUnit.METER_PER_SECOND,
            MeasureUnit.KILOMETER_PER_HOUR,
            MeasureUnit.KNOT
        )
    ),
    TIME(
        listOf(
            MeasureUnit.DAY,
            MeasureUnit.HOUR,
            MeasureUnit.MINUTE,
            MeasureUnit.SECOND,
            MeasureUnit.MILLISECOND,
        )
    ),
    LENGTH(
        listOf(
            MeasureUnit.FOOT,
            MeasureUnit.KILOMETER,
            MeasureUnit.METER,
            MeasureUnit.CENTIMETER,
            MeasureUnit.MILLIMETER,
            MeasureUnit.MILE,
            MeasureUnit.INCH,
            MeasureUnit.YARD
        )
    ),
    WEIGHT(
        listOf(
            MeasureUnit.POUND,
            MeasureUnit.METRIC_TON,
            /*MeasureUnit.TON,*/
            MeasureUnit.KILOGRAM,
            MeasureUnit.MILLIGRAM,
            MeasureUnit.GRAM,
            MeasureUnit.OUNCE,
            MeasureUnit.STONE
        )
    ),
    AREA(
        listOf(
            MeasureUnit.ACRE,
            MeasureUnit.HECTARE,
            MeasureUnit.SQUARE_METER,
            MeasureUnit.SQUARE_KILOMETER,
            MeasureUnit.SQUARE_FOOT,
            MeasureUnit.SQUARE_INCH,
            MeasureUnit.SQUARE_YARD,
            MeasureUnit.SQUARE_MILE
        )
    );

    companion object{
        @Stable
        fun toDisplayName(context: Context, type: ConverterType): String{
            return context.getString(getStringResourceForType(type))
        }
        fun getStringResourceForType(type: ConverterType): Int{
            return when(type){
                TEMPERATURE -> R.string.temperature
                SPEED -> R.string.speed
                TIME -> R.string.time
                LENGTH -> R.string.length
                WEIGHT -> R.string.weight
                AREA -> R.string.area
            }
        }
        @Stable
        fun getIconForType(type: ConverterType): Int{
            return when(type){
                TEMPERATURE -> R.drawable.ic_temperature
                SPEED -> R.drawable.ic_speed
                TIME -> R.drawable.ic_time
                LENGTH -> R.drawable.ic_length
                WEIGHT -> R.drawable.ic_weight
                AREA -> R.drawable.ic_area
            }
        }
    }
}