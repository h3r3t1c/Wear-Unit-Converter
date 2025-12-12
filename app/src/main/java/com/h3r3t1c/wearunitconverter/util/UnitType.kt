package com.h3r3t1c.wearunitconverter.util

import android.icu.text.MeasureFormat
import android.icu.util.MeasureUnit
import android.icu.util.ULocale

object UnitType {

    const val UNIT_TYPE_TEMPERATURE_FAHRENHEIT = 0
    const val UNIT_TYPE_TEMPERATURE_CELSIUS = 1
    const val UNIT_TYPE_TEMPERATURE_KELVIN = 2

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
    const val UNIT_TYPE_WEIGHT_TON_METRIC = 23
    const val UNIT_TYPE_WEIGHT_KILOGRAM = 24
    const val UNIT_TYPE_WEIGHT_GRAM = 25
    const val UNIT_TYPE_WEIGHT_MILLIGRAM = 26
    const val UNIT_TYPE_WEIGHT_TON_UK = 27
    const val UNIT_TYPE_WEIGHT_TON_US = 28
    const val UNIT_TYPE_WEIGHT_STONE_UK = 29


    val TEMPERATURE_UNITS = listOf( UNIT_TYPE_TEMPERATURE_FAHRENHEIT,UNIT_TYPE_TEMPERATURE_CELSIUS, UNIT_TYPE_TEMPERATURE_KELVIN)
    val SPEED_UNITS = listOf(UNIT_TYPE_SPEED_MILE_PER_HOUR,UNIT_TYPE_SPEED_FOOT_PER_SECOND,UNIT_TYPE_SPEED_METER_PER_SECOND,UNIT_TYPE_SPEED_KM_PER_HOUR,UNIT_TYPE_SPEED_KNOT)
    val TIME_UNITS = listOf(UNIT_TYPE_TIME_MILLS,UNIT_TYPE_TIME_SECOND,UNIT_TYPE_TIME_MINUTE,UNIT_TYPE_TIME_HOUR,UNIT_TYPE_TIME_DAY)
    val LENGTH_UNITS = listOf(UNIT_TYPE_LENGTH_FOOT,UNIT_TYPE_LENGTH_KILOMETER,UNIT_TYPE_LENGTH_METER,UNIT_TYPE_LENGTH_CENTIMETER,UNIT_TYPE_LENGTH_MILLIMETER,
                                    UNIT_TYPE_LENGTH_MILE, UNIT_TYPE_LENGTH_YARD, UNIT_TYPE_LENGTH_INCH)
    val WEIGHT_UNITS = listOf(UNIT_TYPE_WEIGHT_POUND,UNIT_TYPE_WEIGHT_OUNCE,UNIT_TYPE_WEIGHT_TON_METRIC,UNIT_TYPE_WEIGHT_KILOGRAM,UNIT_TYPE_WEIGHT_GRAM,
                                    UNIT_TYPE_WEIGHT_MILLIGRAM, UNIT_TYPE_WEIGHT_TON_UK, UNIT_TYPE_WEIGHT_TON_US, UNIT_TYPE_WEIGHT_STONE_UK)

    private lateinit var unitFormatter:MeasureFormat
    private lateinit var unitFormatterLong : MeasureFormat

    fun getUnitsForConverterType(type: ConverterType): List<Int>{
        return when(type){
            ConverterType.TEMPERATURE -> TEMPERATURE_UNITS
            ConverterType.SPEED -> SPEED_UNITS
            ConverterType.TIME -> TIME_UNITS
            ConverterType.LENGTH -> LENGTH_UNITS
            ConverterType.WEIGHT -> WEIGHT_UNITS
        }
    }

    /**
     *  Important: MeasureFormat does not work with unit testing...
     */
    fun unitTypeToString(type: Int, useFull:Boolean = false):String{
        if(!this::unitFormatter.isInitialized) {
            unitFormatter = MeasureFormat.getInstance(
                ULocale.getDefault(), MeasureFormat.FormatWidth.NUMERIC
            )
            unitFormatterLong = MeasureFormat.getInstance(
                ULocale.getDefault(), MeasureFormat.FormatWidth.WIDE
            )
        }
        unitFormatter.formatMeasures()
        return when(type){
                UNIT_TYPE_TEMPERATURE_FAHRENHEIT -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.FAHRENHEIT) else unitFormatter.getUnitDisplayName(MeasureUnit.FAHRENHEIT)
                UNIT_TYPE_TEMPERATURE_CELSIUS -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.CELSIUS) else unitFormatter.getUnitDisplayName(MeasureUnit.CELSIUS)
                UNIT_TYPE_TEMPERATURE_KELVIN -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.KELVIN) else unitFormatter.getUnitDisplayName(MeasureUnit.KELVIN)
                // speed
                UNIT_TYPE_SPEED_MILE_PER_HOUR -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.MILE_PER_HOUR) else if (ULocale.getDefault().equals(ULocale.US)) "mph" else unitFormatter.getUnitDisplayName(MeasureUnit.MILE_PER_HOUR)
                UNIT_TYPE_SPEED_FOOT_PER_SECOND -> if(useFull) "feet per second" else "ft/s"
                UNIT_TYPE_SPEED_METER_PER_SECOND -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.METER_PER_SECOND) else unitFormatter.getUnitDisplayName(MeasureUnit.METER_PER_SECOND)
                UNIT_TYPE_SPEED_KM_PER_HOUR -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.KILOMETER_PER_HOUR) else unitFormatter.getUnitDisplayName(MeasureUnit.KILOMETER_PER_HOUR)
                UNIT_TYPE_SPEED_KNOT-> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.KNOT) else unitFormatter.getUnitDisplayName(MeasureUnit.KNOT)
                // length
                UNIT_TYPE_LENGTH_FOOT -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.FOOT) else unitFormatter.getUnitDisplayName(MeasureUnit.FOOT)
                UNIT_TYPE_LENGTH_KILOMETER -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.KILOMETER) else unitFormatter.getUnitDisplayName(MeasureUnit.KILOMETER)
                UNIT_TYPE_LENGTH_METER -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.METER) else unitFormatter.getUnitDisplayName(MeasureUnit.METER)
                UNIT_TYPE_LENGTH_CENTIMETER -> if (useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.CENTIMETER) else unitFormatter.getUnitDisplayName(MeasureUnit.CENTIMETER)
                UNIT_TYPE_LENGTH_MILLIMETER -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.MILLIMETER) else unitFormatter.getUnitDisplayName(MeasureUnit.MILLIMETER)
                UNIT_TYPE_LENGTH_MILE -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.MILE) else unitFormatter.getUnitDisplayName(MeasureUnit.MILE)
                UNIT_TYPE_LENGTH_YARD -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.YARD) else unitFormatter.getUnitDisplayName(MeasureUnit.YARD)
                UNIT_TYPE_LENGTH_INCH -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.INCH) else unitFormatter.getUnitDisplayName(MeasureUnit.INCH)
                // weight
                UNIT_TYPE_WEIGHT_POUND -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.POUND) else unitFormatter.getUnitDisplayName(MeasureUnit.POUND)
                UNIT_TYPE_WEIGHT_OUNCE -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.OUNCE) else unitFormatter.getUnitDisplayName(MeasureUnit.OUNCE)
                UNIT_TYPE_WEIGHT_TON_METRIC -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.METRIC_TON) else unitFormatter.getUnitDisplayName(MeasureUnit.METRIC_TON)
                UNIT_TYPE_WEIGHT_KILOGRAM -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.KILOGRAM) else unitFormatter.getUnitDisplayName(MeasureUnit.KILOGRAM)
                UNIT_TYPE_WEIGHT_GRAM -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.GRAM) else unitFormatter.getUnitDisplayName(MeasureUnit.GRAM)
                UNIT_TYPE_WEIGHT_MILLIGRAM -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.MILLIGRAM) else unitFormatter.getUnitDisplayName(MeasureUnit.MILLIGRAM)
                UNIT_TYPE_WEIGHT_TON_UK -> "ton (UK)"
                UNIT_TYPE_WEIGHT_TON_US -> "ton (US)"
                UNIT_TYPE_WEIGHT_STONE_UK -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.STONE) else unitFormatter.getUnitDisplayName(MeasureUnit.STONE)
                // time
                UNIT_TYPE_TIME_MILLS -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.MILLISECOND) else unitFormatter.getUnitDisplayName(MeasureUnit.MILLISECOND)
                UNIT_TYPE_TIME_SECOND -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.SECOND) else unitFormatter.getUnitDisplayName(MeasureUnit.SECOND)
                UNIT_TYPE_TIME_MINUTE -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.MINUTE) else unitFormatter.getUnitDisplayName(MeasureUnit.MINUTE)
                UNIT_TYPE_TIME_HOUR -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.HOUR) else unitFormatter.getUnitDisplayName(MeasureUnit.HOUR)
                UNIT_TYPE_TIME_DAY -> if(useFull) unitFormatterLong.getUnitDisplayName(MeasureUnit.DAY) else unitFormatter.getUnitDisplayName(MeasureUnit.DAY)
                else ->"?"
            }
    }
}