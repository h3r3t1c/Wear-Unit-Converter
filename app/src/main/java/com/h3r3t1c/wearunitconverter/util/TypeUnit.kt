package com.h3r3t1c.wearunitconverter.util

import android.icu.text.MeasureFormat
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
import androidx.compose.runtime.Stable

enum class TypeUnit(val measureUnit: MeasureUnit? = null) {
    // temperature
    FAHRENHEIT(MeasureUnit.FAHRENHEIT),
    CELSIUS(MeasureUnit.CELSIUS),
    KELVIN(MeasureUnit.KELVIN),
    // speed
    MILE_PER_HOUR(MeasureUnit.MILE_PER_HOUR),
    METER_PER_SECOND(MeasureUnit.METER_PER_SECOND),
    KILOMETER_PER_HOUR(MeasureUnit.KILOMETER_PER_HOUR),
    KNOT(MeasureUnit.KNOT),
    // time
    DAY(MeasureUnit.DAY),
    HOUR(MeasureUnit.HOUR),
    MINUTE(MeasureUnit.MINUTE),
    SECOND(MeasureUnit.SECOND),
    MILLISECOND(MeasureUnit.MILLISECOND),
    // length
    FOOT(MeasureUnit.FOOT),
    KILOMETER(MeasureUnit.KILOMETER),
    METER(MeasureUnit.METER),
    CENTIMETER(MeasureUnit.CENTIMETER),
    MILLIMETER(MeasureUnit.MILLIMETER),
    MILE(MeasureUnit.MILE),
    INCH(MeasureUnit.INCH),
    YARD(MeasureUnit.YARD),
    // weight
    POUND(MeasureUnit.POUND),
    METRIC_TON(MeasureUnit.METRIC_TON),
    //TON(MeasureUnit.TON),
    KILOGRAM(MeasureUnit.KILOGRAM),
    MILLIGRAM(MeasureUnit.MILLIGRAM),
    GRAM(MeasureUnit.GRAM),
    OUNCE(MeasureUnit.OUNCE),
    STONE(MeasureUnit.STONE),
    // area
    ACRE(MeasureUnit.ACRE),
    HECTARE(MeasureUnit.HECTARE),
    SQUARE_METER(MeasureUnit.SQUARE_METER),
    SQUARE_KILOMETER(MeasureUnit.SQUARE_KILOMETER),
    SQUARE_FOOT(MeasureUnit.SQUARE_FOOT),
    SQUARE_INCH(MeasureUnit.SQUARE_INCH),
    SQUARE_YARD(MeasureUnit.SQUARE_YARD),
    SQUARE_MILE(MeasureUnit.SQUARE_MILE),
    // pressure
    BAR(),
    MILLIBAR(MeasureUnit.MILLIBAR),
    MILLIMETER_OF_MERCURY(MeasureUnit.MILLIMETER_OF_MERCURY),
    POUND_PER_SQUARE_INCH(MeasureUnit.POUND_PER_SQUARE_INCH),
    ATMOSPHERE(),
    PASCAL(),
    KILOPASCAL(),
    INCH_OF_MERCURY(MeasureUnit.INCH_HG),
    ;

    companion object{
        private lateinit var unitFormatter:MeasureFormat
        private lateinit var unitFormatterLong : MeasureFormat

        @Stable
        fun unitToString(unit: TypeUnit, full:Boolean = false):String{
            if(!this::unitFormatter.isInitialized) {
                unitFormatter = MeasureFormat.getInstance(
                    ULocale.getDefault(), MeasureFormat.FormatWidth.NUMERIC
                )
                unitFormatterLong = MeasureFormat.getInstance(
                    ULocale.getDefault(), MeasureFormat.FormatWidth.WIDE
                )
            }
            return if(unit.measureUnit != null) {
                if (full) unitFormatterLong.getUnitDisplayName(unit.measureUnit) else unitFormatter.getUnitDisplayName(unit.measureUnit)
            }else{
                when(unit){
                    ATMOSPHERE -> {
                        if(full) "standard atmosphere" else "atm"
                    }
                    BAR -> {
                        if(full) "bar" else "bar"
                    }
                    PASCAL -> {
                        if(full) "pascal" else "Pa"
                    }
                    KILOPASCAL -> {
                        if(full) "kilopascal" else "kPa"
                    }
                    else -> ""
                }
            }
        }
    }
}