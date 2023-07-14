package com.h3r3t1c.wearunitconverter.util

import java.text.DecimalFormat
import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.time.Duration.Companion.convert
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

object Converter {

    lateinit var numberFormat:DecimalFormat
    lateinit var timeNumberFormat: DecimalFormat

    val supportedTimedUnits = listOf(DurationUnit.MILLISECONDS, DurationUnit.SECONDS, DurationUnit.MINUTES, DurationUnit.HOURS, DurationUnit.DAYS)

    fun fToC(f:Double):Double = (f-32.0)*(0.55555555555)
    fun fToK(f:Double):Double = (f-32.0)*(0.55555555555)+273.15

    fun cToF(c:Double):Double = (c*(1.8))+32.0
    fun cToK(c:Double):Double = c+273.15f

    fun kToF(k:Double):Double = (k-273.15)*(1.8)+32.0
    fun kToC(k:Double):Double = k-273.15

    @OptIn(ExperimentalTime::class)
    fun convertTime(value:Double, fromUnit: Int, toUnit:Int):String{
        if(!this::timeNumberFormat.isInitialized){
            timeNumberFormat = DecimalFormat("0.####E0")
        }
        return convert(value, supportedTimedUnits[fromUnit-8], supportedTimedUnits[toUnit-8]).toString()
    }
    private fun convertLength(numIn:Double, unitIn:Int, unitOut:Int): String{
        val out = when(unitIn){
            UnitType.UNIT_TYPE_LENGTH_FOOT ->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn / 3281.0
                    UnitType.UNIT_TYPE_LENGTH_METER -> numIn / 3.281
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER  -> numIn * 30.48
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 304.8
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn / 5280.0
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn / 3.0
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 12.0
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_KILOMETER->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn * 3281.0
                    UnitType.UNIT_TYPE_LENGTH_METER -> numIn * 1000.0
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER  -> numIn * 100000
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 1e+6
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn / 1.609
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn * 1094.0
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 39370.0
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_METER->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn * 3.281
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn / 1000.0
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER  -> numIn * 100.0
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 1000.0
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn / 1609.0
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn * 1.094
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 39.37
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_CENTIMETER->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn / 100000.0
                    UnitType.UNIT_TYPE_LENGTH_METER  -> numIn / 100.0
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 10.0
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn / 160900.0
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn / 91.44
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn / 30.48
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn / 2.54
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_MILLIMETER->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn / 1e+6
                    UnitType.UNIT_TYPE_LENGTH_METER  -> numIn / 1000.0
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER -> numIn / 10.0
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn / 1.609e+6
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn / 914.4
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn / 304.8
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn / 25.4
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_MILE->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn * 1.609
                    UnitType.UNIT_TYPE_LENGTH_METER  -> numIn * 1609.34
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER -> numIn * 160934
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 1.609e+6
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn * 1960.0
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn * 5280.0
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 63360.0
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_YARD->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn / 1094.0
                    UnitType.UNIT_TYPE_LENGTH_METER  -> numIn / 1.094
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER -> numIn * 91.44
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 914.4
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn / 1760.0
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn * 3.0
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 36.0
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_INCH->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn / 39370.0
                    UnitType.UNIT_TYPE_LENGTH_METER  -> numIn / 39.37
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER -> numIn * 2.54
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 25.4
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn / 63360.0
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn / 36.0
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn / 12.0
                    else -> numIn
                }
            }
            else  -> numIn
        }
        return formatNumber(out)
    }
    private fun convertWeights(numIn:Double, unitIn:Int, unitOut:Int) : String{
        val out = when(unitIn){
            UnitType.UNIT_TYPE_WEIGHT_METRIC_TON -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 1000.0
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 1e+6
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 1e+9
                    UnitType.UNIT_TYPE_WEIGHT_IMPERIAL_TON -> numIn / 1.016
                    UnitType.UNIT_TYPE_WEIGHT_US_TON -> numIn * 1.102
                    UnitType.UNIT_TYPE_WEIGHT_STONE -> numIn * 157.5
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 2204.62
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 35270.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_METRIC_TON -> numIn / 1000.0
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 1000.0
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 1e+6
                    UnitType.UNIT_TYPE_WEIGHT_IMPERIAL_TON -> numIn / 1016.0
                    UnitType.UNIT_TYPE_WEIGHT_US_TON -> numIn / 907.2
                    UnitType.UNIT_TYPE_WEIGHT_STONE -> numIn / 6.35
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 2.205
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 35.274
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_GRAM -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_METRIC_TON -> numIn / 1e+6
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn / 1000.0
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 1000
                    UnitType.UNIT_TYPE_WEIGHT_IMPERIAL_TON -> numIn / 1.016e+6
                    UnitType.UNIT_TYPE_WEIGHT_US_TON -> numIn / 907200.0
                    UnitType.UNIT_TYPE_WEIGHT_STONE -> numIn / 6350.0
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn / 453.6
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn / 28.35
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_METRIC_TON -> numIn / 1e+9
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn / 1e+6
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn / 1000
                    UnitType.UNIT_TYPE_WEIGHT_IMPERIAL_TON -> numIn / 1.016e+9
                    UnitType.UNIT_TYPE_WEIGHT_US_TON -> numIn / 9.072e+8
                    UnitType.UNIT_TYPE_WEIGHT_STONE -> numIn / 6.35e+6
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn / 453600.0
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn / 28350.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_IMPERIAL_TON -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_METRIC_TON -> numIn / 1.01605
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 1016.05
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 1.016e+6
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 1.016e+9
                    UnitType.UNIT_TYPE_WEIGHT_US_TON -> numIn * 1.12
                    UnitType.UNIT_TYPE_WEIGHT_STONE -> numIn * 160.0
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 2240.0
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 35840.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_US_TON -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_METRIC_TON -> numIn / 1.102
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 907.2
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 907200
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 9.072e+8
                    UnitType.UNIT_TYPE_WEIGHT_IMPERIAL_TON -> numIn / 1.12
                    UnitType.UNIT_TYPE_WEIGHT_STONE -> numIn * 142.857
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 2000.0
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 32000.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_STONE -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_METRIC_TON -> numIn / 157.5
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 6.35
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 6350.0
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 6.35e+6
                    UnitType.UNIT_TYPE_WEIGHT_IMPERIAL_TON -> numIn / 160.0
                    UnitType.UNIT_TYPE_WEIGHT_US_TON -> numIn / 142.857
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 14.0
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 224.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_POUND -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_METRIC_TON -> numIn / 2250
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn / 2.205
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 453.592
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 453592
                    UnitType.UNIT_TYPE_WEIGHT_IMPERIAL_TON -> numIn / 2240.0
                    UnitType.UNIT_TYPE_WEIGHT_US_TON -> numIn / 2000.0
                    UnitType.UNIT_TYPE_WEIGHT_STONE -> numIn / 10
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 16
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_OUNCE -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_METRIC_TON -> numIn / 35270.0
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn / 35.274
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 28.3495
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 28350.0
                    UnitType.UNIT_TYPE_WEIGHT_IMPERIAL_TON -> numIn / 35840.0
                    UnitType.UNIT_TYPE_WEIGHT_US_TON -> numIn / 32000.0
                    UnitType.UNIT_TYPE_WEIGHT_STONE -> numIn / 224.0
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn / 16
                    else -> numIn
                }
            }

            else -> numIn
        }
        return formatNumber(out)
    }
    private fun convertSpeed(numIn:Double, unitIn:Int, unitOut:Int):String{
        val out = when(unitIn){
            UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND -> numIn * 1.46667
                    UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND -> numIn / 2.237
                    UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> numIn * 1.60934
                    UnitType.UNIT_TYPE_SPEED_KNOT -> numIn / 1.151
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR -> numIn / 1.46667
                    UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND -> numIn / 3.281
                    UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> numIn * 1.09728
                    UnitType.UNIT_TYPE_SPEED_KNOT -> numIn / 1.688
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR -> numIn * 2.23694
                    UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND -> numIn * 3.28084
                    UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> numIn * 3.6
                    UnitType.UNIT_TYPE_SPEED_KNOT -> numIn * 1.94384
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR -> numIn / 0.621371
                    UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND -> numIn / 0.9113344
                    UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND -> numIn / 3.6
                    UnitType.UNIT_TYPE_SPEED_KNOT -> numIn / 1.852
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_SPEED_KNOT -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR -> numIn * 1.15078
                    UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND -> numIn * 1.68781
                    UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND -> numIn / 1944
                    UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> numIn * 1.852
                    else -> numIn
                }
            }
            else -> numIn
        }
        return formatNumber(out)
    }
    fun convertUnits(numIn:Double, unitIn:Int, unitOut:Int):String{
        if(UnitType.TIME_UNITS.contains(unitIn)){
            return convertTime(numIn,unitIn, unitOut)
        }
        else if(UnitType.LENGTH_UNITS.contains(unitIn)){
            return convertLength(numIn, unitIn, unitOut)
        }
        else if(UnitType.WEIGHT_UNITS.contains(unitIn)){
            return convertWeights(numIn, unitIn, unitOut)
        }
        else if(UnitType.SPEED_UNITS.contains(unitIn)){
            return convertSpeed(numIn, unitIn, unitOut)
        }
        else {
            val out = when (unitIn) {
                UnitType.UNIT_TYPE_FAHRENHEIT -> {
                    when (unitOut) {
                        UnitType.UNIT_TYPE_CELSIUS -> fToC(numIn)
                        UnitType.UNIT_TYPE_KELVIN -> fToK(numIn)
                        else -> numIn
                    }
                }

                UnitType.UNIT_TYPE_CELSIUS -> {
                    when (unitOut) {
                        UnitType.UNIT_TYPE_FAHRENHEIT -> cToF(numIn)
                        UnitType.UNIT_TYPE_KELVIN -> cToK(numIn)
                        else -> numIn
                    }
                }

                UnitType.UNIT_TYPE_KELVIN -> {
                    when (unitOut) {
                        UnitType.UNIT_TYPE_FAHRENHEIT -> kToF(numIn)
                        UnitType.UNIT_TYPE_CELSIUS -> kToC(numIn)
                        else -> numIn
                    }
                }

                else -> numIn
            }
            return formatNumber(out)
        }
    }

    private fun formatNumber(d:Double?):String{
        if(!this::numberFormat.isInitialized){
            numberFormat = DecimalFormat("0.####")
        }
        val tmp = numberFormat.format(d)
        return if(tmp.equals("-0")) "0" else tmp
    }
}