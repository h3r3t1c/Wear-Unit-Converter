package com.h3r3t1c.wearunitconverter.util

import android.icu.text.MeasureFormat
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
import com.h3r3t1c.wearunitconverter.presentation.ConverterType
import java.util.Locale

object UnitType {

    const val UNIT_TYPE_FAHRENHEIT = 0
    const val UNIT_TYPE_CELSIUS = 1
    const val UNIT_TYPE_KELVIN = 2

    const val UNIT_TYPE_SPEED_MILE_PER_HOUR = 3
    const val UNIT_TYPE_SPEED_FOOT_PER_SECOND = 4
    const val UNIT_TYPE_SPEED_METER_PER_SECOND = 5
    const val UNIT_TYPE_SPEED_KM_PER_HOUR = 6
    const val UNIT_TYPE_SPEED_KNOT = 7

    const val UNIT_TYPE_TIME_MILLS = 8
    const val UNIT_TYPE_TIME_SECOND = 9
    const val UNIT_TYPE_TIME_MINUTE = 10
    const val UNIT_TYPE_TIME_HOUR = 11
    const val UNIT_TYPE_TIME_DAY = 12

    const val UNIT_TYPE_LENGTH_FOOT = 13
    const val UNIT_TYPE_LENGTH_KILOMETER = 14
    const val UNIT_TYPE_LENGTH_METER = 15
    const val UNIT_TYPE_LENGTH_CENTIMETER = 16
    const val UNIT_TYPE_LENGTH_MILLIMETER = 17
    const val UNIT_TYPE_LENGTH_MILE = 18
    const val UNIT_TYPE_LENGTH_YARD = 19
    const val UNIT_TYPE_LENGTH_INCH = 20

    const val UNIT_TYPE_WEIGHT_POUND = 21
    const val UNIT_TYPE_WEIGHT_OUNCE = 22
    const val UNIT_TYPE_WEIGHT_METRIC_TON = 23
    const val UNIT_TYPE_WEIGHT_KILOGRAM = 24
    const val UNIT_TYPE_WEIGHT_GRAM = 25
    const val UNIT_TYPE_WEIGHT_MILLIGRAM = 26
    const val UNIT_TYPE_WEIGHT_IMPERIAL_TON = 27
    const val UNIT_TYPE_WEIGHT_US_TON = 28
    const val UNIT_TYPE_WEIGHT_STONE = 29


    val TEMP_UNITS = arrayOf( UNIT_TYPE_FAHRENHEIT,UNIT_TYPE_CELSIUS, UNIT_TYPE_KELVIN)
    val SPEED_UNITS = arrayOf(UNIT_TYPE_SPEED_MILE_PER_HOUR,UNIT_TYPE_SPEED_FOOT_PER_SECOND,UNIT_TYPE_SPEED_METER_PER_SECOND,UNIT_TYPE_SPEED_KM_PER_HOUR,UNIT_TYPE_SPEED_KNOT)
    val TIME_UNITS = arrayOf(UNIT_TYPE_TIME_MILLS,UNIT_TYPE_TIME_SECOND,UNIT_TYPE_TIME_MINUTE,UNIT_TYPE_TIME_HOUR,UNIT_TYPE_TIME_DAY)
    val LENGTH_UNITS = arrayOf(UNIT_TYPE_LENGTH_FOOT,UNIT_TYPE_LENGTH_KILOMETER,UNIT_TYPE_LENGTH_METER,UNIT_TYPE_LENGTH_CENTIMETER,UNIT_TYPE_LENGTH_MILLIMETER,
        UNIT_TYPE_LENGTH_MILE, UNIT_TYPE_LENGTH_YARD, UNIT_TYPE_LENGTH_INCH)
    val WEIGHT_UNITS = arrayOf(UNIT_TYPE_WEIGHT_POUND,UNIT_TYPE_WEIGHT_OUNCE,UNIT_TYPE_WEIGHT_METRIC_TON,UNIT_TYPE_WEIGHT_KILOGRAM,UNIT_TYPE_WEIGHT_GRAM,
        UNIT_TYPE_WEIGHT_MILLIGRAM, UNIT_TYPE_WEIGHT_IMPERIAL_TON, UNIT_TYPE_WEIGHT_US_TON, UNIT_TYPE_WEIGHT_STONE)

    private lateinit var unitFormatter:MeasureFormat
    private lateinit var unitFormatterShort:MeasureFormat

    fun unitTypeToString(type: Int):String{
        if(!this::unitFormatter.isInitialized) {
            unitFormatter = MeasureFormat.getInstance(
                ULocale.getDefault(), MeasureFormat.FormatWidth.NARROW
            )
            unitFormatterShort = MeasureFormat.getInstance(
                ULocale.getDefault(), MeasureFormat.FormatWidth.SHORT
            )
        }
        return if(type in 8..12){
            Converter.supportedTimedUnits[type-8].name.lowercase(Locale.ROOT).replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        } else
            when(type){
                UNIT_TYPE_FAHRENHEIT -> unitFormatter.getUnitDisplayName(MeasureUnit.FAHRENHEIT)
                UNIT_TYPE_CELSIUS -> unitFormatter.getUnitDisplayName(MeasureUnit.CELSIUS)
                UNIT_TYPE_KELVIN -> unitFormatter.getUnitDisplayName(MeasureUnit.KELVIN)
                // speed
                UNIT_TYPE_SPEED_MILE_PER_HOUR -> if (ULocale.getDefault().equals(ULocale.US)) "mph" else unitFormatter.getUnitDisplayName(MeasureUnit.MILE_PER_HOUR)
                UNIT_TYPE_SPEED_FOOT_PER_SECOND -> "ft/s"
                UNIT_TYPE_SPEED_METER_PER_SECOND -> unitFormatter.getUnitDisplayName(MeasureUnit.METER_PER_SECOND)
                UNIT_TYPE_SPEED_KM_PER_HOUR -> unitFormatter.getUnitDisplayName(MeasureUnit.KILOMETER_PER_HOUR)
                UNIT_TYPE_SPEED_KNOT-> unitFormatter.getUnitDisplayName(MeasureUnit.KNOT)
                // length
                UNIT_TYPE_LENGTH_FOOT -> unitFormatterShort.getUnitDisplayName(MeasureUnit.FOOT)
                UNIT_TYPE_LENGTH_KILOMETER -> unitFormatterShort.getUnitDisplayName(MeasureUnit.KILOMETER)
                UNIT_TYPE_LENGTH_METER -> unitFormatterShort.getUnitDisplayName(MeasureUnit.METER)
                UNIT_TYPE_LENGTH_CENTIMETER -> unitFormatterShort.getUnitDisplayName(MeasureUnit.CENTIMETER)
                UNIT_TYPE_LENGTH_MILLIMETER -> unitFormatterShort.getUnitDisplayName(MeasureUnit.MILLIMETER)
                UNIT_TYPE_LENGTH_MILE -> unitFormatterShort.getUnitDisplayName(MeasureUnit.MILE)
                UNIT_TYPE_LENGTH_YARD -> unitFormatterShort.getUnitDisplayName(MeasureUnit.YARD)
                UNIT_TYPE_LENGTH_INCH -> unitFormatterShort.getUnitDisplayName(MeasureUnit.INCH)
                // weight
                UNIT_TYPE_WEIGHT_POUND -> unitFormatter.getUnitDisplayName(MeasureUnit.POUND)
                UNIT_TYPE_WEIGHT_OUNCE -> unitFormatter.getUnitDisplayName(MeasureUnit.OUNCE)
                UNIT_TYPE_WEIGHT_METRIC_TON -> "Metric Ton"
                UNIT_TYPE_WEIGHT_KILOGRAM -> unitFormatter.getUnitDisplayName(MeasureUnit.KILOGRAM)
                UNIT_TYPE_WEIGHT_GRAM -> unitFormatter.getUnitDisplayName(MeasureUnit.GRAM)
                UNIT_TYPE_WEIGHT_MILLIGRAM -> unitFormatter.getUnitDisplayName(MeasureUnit.MILLIGRAM)
                UNIT_TYPE_WEIGHT_IMPERIAL_TON -> "Imperial Ton"
                UNIT_TYPE_WEIGHT_US_TON -> unitFormatter.getUnitDisplayName(MeasureUnit.TON)
                UNIT_TYPE_WEIGHT_STONE -> unitFormatter.getUnitDisplayName(MeasureUnit.STONE)
                else ->"?"
            }
    }
    fun getUnitTypeList(type:Int):Array<Int>{
        return when(type){
            ConverterType.TYPE_TEMPERATURE -> TEMP_UNITS
            ConverterType.TYPE_SPEED -> SPEED_UNITS
            ConverterType.TYPE_TIME -> TIME_UNITS
            ConverterType.TYPE_LENGTH -> LENGTH_UNITS
            ConverterType.TYPE_WEIGHT -> WEIGHT_UNITS
            else -> TEMP_UNITS
        }
    }

}