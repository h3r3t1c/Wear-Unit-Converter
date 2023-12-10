package com.h3r3t1c.wearunitconverter.util

import android.content.Context
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

object ConvertHelper {


    private val supportedTimedUnits = listOf(DurationUnit.MILLISECONDS, DurationUnit.SECONDS, DurationUnit.MINUTES, DurationUnit.HOURS, DurationUnit.DAYS)

    private fun fToC(f:Double):Double = (f-32.0)*(0.55555555556)
    private fun fToK(f:Double):Double = fToC(f)+273.15

    private fun cToF(c:Double):Double = (c*(1.8))+32.0
    private fun cToK(c:Double):Double = c+273.15

    private fun kToF(k:Double):Double = kToC(k)*(1.8)+32.0
    private fun kToC(k:Double):Double = k-273.15


    @OptIn(ExperimentalTime::class)
    fun convertTimeRaw(value:Double, fromUnit: Int, toUnit:Int):Double{
        return Duration.convert(value, supportedTimedUnits[fromUnit-8], supportedTimedUnits[toUnit-8])
    }
    private fun convertLength(numIn:Double, unitIn:Int, unitOut:Int): Double{
        return when(unitIn){
            UnitType.UNIT_TYPE_LENGTH_FOOT ->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn * 0.0003048
                    UnitType.UNIT_TYPE_LENGTH_METER -> numIn * 0.3048
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER  -> numIn * 30.48
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 304.8
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn * 0.0001893939
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn * (1.0/3.0)
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 12.0
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_KILOMETER->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn * 3280.839895
                    UnitType.UNIT_TYPE_LENGTH_METER -> numIn * 1000.0
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER  -> numIn * 100000.0
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 1e+6
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn * 0.6213711922
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn * 1093.6132983
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 39370.07874
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_METER->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn * 3.280839895
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn * 0.001
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER  -> numIn * 100.0
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 1000.0
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn * 0.0006213712
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn * 1.0936132983
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 39.37007874
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_CENTIMETER->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn * 0.00001
                    UnitType.UNIT_TYPE_LENGTH_METER  -> numIn * 0.01
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 10.0
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn * 0.0000062137
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn * 0.010936133
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn * 0.032808399
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 0.3937007874
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_MILLIMETER->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn * 0.000001
                    UnitType.UNIT_TYPE_LENGTH_METER  -> numIn * 0.001
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER -> numIn * 0.1
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn * 6.213711922E-7
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn * 0.0010936133
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn * 0.0032808399
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 0.0393700787
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_MILE->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn * 1.609344
                    UnitType.UNIT_TYPE_LENGTH_METER  -> numIn * 1609.344
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER -> numIn * 160934.4
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 1609344
                    UnitType.UNIT_TYPE_LENGTH_YARD -> numIn * 1760.0
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn * 5280.0
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 63360.0
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_YARD->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn * 0.0009144
                    UnitType.UNIT_TYPE_LENGTH_METER  -> numIn * 0.9144
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER -> numIn * 91.44
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 914.4
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn * 0.0005681818
                    UnitType.UNIT_TYPE_LENGTH_FOOT -> numIn * 3.0
                    UnitType.UNIT_TYPE_LENGTH_INCH -> numIn * 36.0
                    else -> numIn
                }
            }
            UnitType.UNIT_TYPE_LENGTH_INCH->{
                when(unitOut){
                    UnitType.UNIT_TYPE_LENGTH_KILOMETER -> numIn * 0.0000254
                    UnitType.UNIT_TYPE_LENGTH_METER  -> numIn * 0.0254
                    UnitType.UNIT_TYPE_LENGTH_CENTIMETER -> numIn * 2.54
                    UnitType.UNIT_TYPE_LENGTH_MILLIMETER -> numIn * 25.4
                    UnitType.UNIT_TYPE_LENGTH_MILE -> numIn * 0.0000157828
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
            UnitType.UNIT_TYPE_WEIGHT_TON_METRIC -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 1000.0
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 1000000.0
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 1000000000
                    UnitType.UNIT_TYPE_WEIGHT_TON_UK -> numIn * 0.9842065276
                    UnitType.UNIT_TYPE_WEIGHT_TON_US -> numIn * 1.1023113109
                    UnitType.UNIT_TYPE_WEIGHT_STONE_UK -> numIn * 157.47304442
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 2204.6226218
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 35273.96195
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_TON_METRIC -> numIn * 0.001
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 1000.0
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 1000000
                    UnitType.UNIT_TYPE_WEIGHT_TON_UK -> numIn * 0.0009842065
                    UnitType.UNIT_TYPE_WEIGHT_TON_US -> numIn * 0.0011023113
                    UnitType.UNIT_TYPE_WEIGHT_STONE_UK -> numIn * 0.1574730444
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 2.2046226218
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 35.27396195
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_GRAM -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_TON_METRIC  -> numIn * 0.000001
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 0.001
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 1000.0
                    UnitType.UNIT_TYPE_WEIGHT_TON_UK -> numIn * 9.842065276E-7
                    UnitType.UNIT_TYPE_WEIGHT_TON_US -> numIn * 0.0000011023
                    UnitType.UNIT_TYPE_WEIGHT_STONE_UK -> numIn * 0.000157473
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 0.0022046226
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 0.0352739619
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_TON_METRIC -> numIn * 1.0E-9
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 0.000001
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 0.001
                    UnitType.UNIT_TYPE_WEIGHT_TON_UK -> numIn * 9.842065276E-10
                    UnitType.UNIT_TYPE_WEIGHT_TON_US -> numIn * 1.10231131E-9
                    UnitType.UNIT_TYPE_WEIGHT_STONE_UK -> numIn * 1.574730444E-7
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 0.0000022046
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 0.000035274
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_TON_UK -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_TON_METRIC -> numIn * 1.0160469088
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 1016.0469088
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 1016046.9088
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 1016046908.8
                    UnitType.UNIT_TYPE_WEIGHT_TON_US -> numIn * 1.12
                    UnitType.UNIT_TYPE_WEIGHT_STONE_UK -> numIn * 160.0
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 2240.0
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 35840.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_TON_US -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_TON_METRIC -> numIn * 0.90718474
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 907.18474
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 907184.74
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 907184740
                    UnitType.UNIT_TYPE_WEIGHT_TON_UK -> numIn * 0.8928571429
                    UnitType.UNIT_TYPE_WEIGHT_STONE_UK -> numIn * 142.85714286
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 2000.0
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 32000.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_STONE_UK -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_TON_METRIC -> numIn * 0.0063502932
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 6.35029318
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 6350.29318
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 6350293.18
                    UnitType.UNIT_TYPE_WEIGHT_TON_UK -> numIn * 0.00625
                    UnitType.UNIT_TYPE_WEIGHT_TON_US -> numIn * 0.007
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn * 14.0
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 224.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_POUND -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_TON_METRIC -> numIn * 0.0004535924
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 0.45359237
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 453.59237
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 453592.37
                    UnitType.UNIT_TYPE_WEIGHT_TON_UK -> numIn * 0.0004464286
                    UnitType.UNIT_TYPE_WEIGHT_TON_US -> numIn * 0.0005
                    UnitType.UNIT_TYPE_WEIGHT_STONE_UK -> numIn * 0.0714285714
                    UnitType.UNIT_TYPE_WEIGHT_OUNCE -> numIn * 16.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_OUNCE -> {
                when (unitOut) {
                    UnitType.UNIT_TYPE_WEIGHT_TON_METRIC -> numIn * 0.0000283495
                    UnitType.UNIT_TYPE_WEIGHT_KILOGRAM -> numIn * 0.0283495231
                    UnitType.UNIT_TYPE_WEIGHT_GRAM -> numIn * 28.349523125
                    UnitType.UNIT_TYPE_WEIGHT_MILLIGRAM -> numIn * 28349.523125
                    UnitType.UNIT_TYPE_WEIGHT_TON_UK -> numIn * 0.0000279018
                    UnitType.UNIT_TYPE_WEIGHT_TON_US -> numIn * 0.00003125
                    UnitType.UNIT_TYPE_WEIGHT_STONE_UK -> numIn * 0.0044642857
                    UnitType.UNIT_TYPE_WEIGHT_POUND -> numIn / 16.0 // ok
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

            UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> {
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
                    UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR -> numIn * 1.150779448
                    UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND -> numIn * 1.6878098571
                    UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND -> numIn * 0.5144444444
                    UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR -> numIn * 1.852
                    else -> numIn
                }
            }
            else -> numIn
        }
    }
    private fun convertTemperature(numIn:Double, unitIn:Int, unitOut:Int):Double {
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
    fun convertUnits(context: Context, numIn:Double, unitIn:Int, unitOut:Int):String{
        return formatNumber(context, convertUnitsRaw(numIn, unitIn, unitOut))
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
    private fun formatNumber(context: Context, d:Double):String{
        val bigDecimal = BigDecimal.valueOf(d)
        return bigDecimal.setScale(AppPrefs.getMaxSigDigits(context).coerceAtMost(calcDecimalLen(d)), RoundingMode.UP).toEngineeringString()
    }
    private fun calcDecimalLen(d:Double):Int{
        val text = abs(d).toString()
        val integerPlaces = text.indexOf('.')
        return text.length - integerPlaces - 1
    }
}