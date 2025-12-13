package com.h3r3t1c.wearunitconverter.util

import android.content.Context
import androidx.compose.runtime.Stable
import com.h3r3t1c.wearunitconverter.R

enum class ConverterType(val units: List<TypeUnit>) {
    TEMPERATURE(
        listOf(
            TypeUnit.FAHRENHEIT,
            TypeUnit.CELSIUS,
            TypeUnit.KELVIN
        )
    ),
    SPEED(
        listOf(
            TypeUnit.MILE_PER_HOUR,
            TypeUnit.METER_PER_SECOND,
            TypeUnit.KILOMETER_PER_HOUR,
            TypeUnit.KNOT
        )
    ),
    TIME(
        listOf(
            TypeUnit.DAY,
            TypeUnit.HOUR,
            TypeUnit.MINUTE,
            TypeUnit.SECOND,
            TypeUnit.MILLISECOND,
        )
    ),
    LENGTH(
        listOf(
            TypeUnit.FOOT,
            TypeUnit.KILOMETER,
            TypeUnit.METER,
            TypeUnit.CENTIMETER,
            TypeUnit.MILLIMETER,
            TypeUnit.MILE,
            TypeUnit.INCH,
            TypeUnit.YARD
        )
    ),
    WEIGHT(
        listOf(
            TypeUnit.POUND,
            TypeUnit.METRIC_TON,
            /*MeasureUnit.TON,*/
            TypeUnit.KILOGRAM,
            TypeUnit.MILLIGRAM,
            TypeUnit.GRAM,
            TypeUnit.OUNCE,
            TypeUnit.STONE
        )
    ),
    AREA(
        listOf(
            TypeUnit.ACRE,
            TypeUnit.HECTARE,
            TypeUnit.SQUARE_METER,
            TypeUnit.SQUARE_KILOMETER,
            TypeUnit.SQUARE_FOOT,
            TypeUnit.SQUARE_INCH,
            TypeUnit.SQUARE_YARD,
            TypeUnit.SQUARE_MILE
        )
    ),
    PRESSURE(
        mutableListOf(
            TypeUnit.BAR,
            TypeUnit.MILLIBAR,
            TypeUnit.PASCAL,
            TypeUnit.KILOPASCAL,
            TypeUnit.INCH_OF_MERCURY,
            TypeUnit.MILLIMETER_OF_MERCURY,
            TypeUnit.POUND_PER_SQUARE_INCH,
            TypeUnit.ATMOSPHERE
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
                PRESSURE -> R.string.pressure
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
                PRESSURE -> R.drawable.ic_pressure
            }
        }
    }
}