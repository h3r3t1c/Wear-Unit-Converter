package com.h3r3t1c.wearunitconverter.util

import java.text.DecimalFormat
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

object ConvertHelper {

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
        return convertTimeRaw(value, fromUnit, toUnit).toString()
    }
    @OptIn(ExperimentalTime::class)
    fun convertTimeRaw(value:Double, fromUnit: Int, toUnit:Int):Double{
        return Duration.convert(value, supportedTimedUnits[fromUnit-8], supportedTimedUnits[toUnit-8])
    }
    private fun convertLength(numIn:Double, unitIn:Int, unitOut:Int): Double{
        return when(unitIn){
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

    }
    private fun convertWeights(numIn:Double, unitIn:Int, unitOut:Int) : Double{
        return when(unitIn){
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
    }
    private fun convertSpeed(numIn:Double, unitIn:Int, unitOut:Int):Double{
        return when(unitIn){
            UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND -> numIn * 1.46667
                    UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND -> numIn * 0.44704
                    UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> numIn * 1.60934
                    UnitType.UNIT_TYPE_SPEED_KNOT -> numIn * 0.8689762419
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR -> numIn * 0.6818181818
                    UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND -> numIn * 0.3048
                    UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> numIn * 1.09728
                    UnitType.UNIT_TYPE_SPEED_KNOT -> numIn * 0.5924838013
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR -> numIn * 2.2369362921
                    UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND -> numIn * 3.280839895
                    UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> numIn * 3.6
                    UnitType.UNIT_TYPE_SPEED_KNOT -> numIn * 1.9438444924
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> { // start here
                when (unitOut) {
                    UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR -> numIn * 0.6213711922
                    UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND -> numIn * 0.9113444153
                    UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND -> numIn * 0.2777777778
                    UnitType.UNIT_TYPE_SPEED_KNOT -> numIn * 0.5399568035
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
    }
    fun convertTemperature(numIn:Double, unitIn:Int, unitOut:Int):Double {
        return when (unitIn) {
            UnitType.UNIT_TYPE_TEMPERATURE_FAHRENHEIT -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_TEMPERATURE_CELSIUS -> fToC(numIn)
                    UnitType.UNIT_TYPE_TEMPERATURE_KELVIN -> fToK(numIn)
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_TEMPERATURE_CELSIUS -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_TEMPERATURE_FAHRENHEIT -> cToF(numIn)
                    UnitType.UNIT_TYPE_TEMPERATURE_KELVIN -> cToK(numIn)
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_TEMPERATURE_KELVIN -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_TEMPERATURE_FAHRENHEIT -> kToF(numIn)
                    UnitType.UNIT_TYPE_TEMPERATURE_CELSIUS -> kToC(numIn)
                    else -> numIn
                }
            }

            else -> numIn
        }
    }
    fun convertUnits(numIn:Double, unitIn:Int, unitOut:Int):String{
        return formatNumber(convertUnitsRaw(numIn, unitIn, unitOut))
    }
    fun convertUnitsRaw(numIn:Double, unitIn:Int, unitOut:Int):Double{
        return if(UnitType.TIME_UNITS.contains(unitIn)){
            convertTimeRaw(numIn,unitIn, unitOut)
        } else if(UnitType.LENGTH_UNITS.contains(unitIn)){
            convertLength(numIn, unitIn, unitOut)
        } else if(UnitType.WEIGHT_UNITS.contains(unitIn)){
            convertWeights(numIn, unitIn, unitOut)
        } else if(UnitType.SPEED_UNITS.contains(unitIn)){
            convertSpeed(numIn, unitIn, unitOut)
        } else {
            convertTemperature(numIn,unitIn, unitOut)
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