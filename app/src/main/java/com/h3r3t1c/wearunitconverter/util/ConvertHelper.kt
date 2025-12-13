package com.h3r3t1c.wearunitconverter.util

import android.icu.util.MeasureUnit
import androidx.compose.runtime.Stable
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

object ConvertHelper {


    private val supportedTimedUnits = listOf(
        DurationUnit.MILLISECONDS to MeasureUnit.MILLISECOND,
        DurationUnit.SECONDS to MeasureUnit.SECOND,
        DurationUnit.MINUTES to MeasureUnit.MINUTE,
        DurationUnit.HOURS to MeasureUnit.HOUR,
        DurationUnit.DAYS to MeasureUnit.DAY
    )

    private fun fToC(f:Double):Double = (f-32.0)*(0.55555555556)
    private fun fToK(f:Double):Double = fToC(f)+273.15

    private fun cToF(c:Double):Double = (c*(1.8))+32.0
    private fun cToK(c:Double):Double = c+273.15

    private fun kToF(k:Double):Double = kToC(k)*(1.8)+32.0
    private fun kToC(k:Double):Double = k-273.15


    @OptIn(ExperimentalTime::class)
    fun convertTimeRaw(value:Double, fromUnit: MeasureUnit, toUnit: MeasureUnit):Double{
        val first = supportedTimedUnits.find { it.second == fromUnit }
        val second = supportedTimedUnits.find { it.second == toUnit }
        return Duration.convert(value, first!!.first, second!!.first)
    }
    private fun convertLength(numIn:Double, unitIn: MeasureUnit, unitOut: MeasureUnit): Double{
        return when(unitIn){
            MeasureUnit.FOOT ->{
                when(unitOut){
                    MeasureUnit.KILOMETER -> numIn * 0.0003048
                    MeasureUnit.METER -> numIn * 0.3048
                    MeasureUnit.CENTIMETER  -> numIn * 30.48
                    MeasureUnit.MILLIMETER -> numIn * 304.8
                    MeasureUnit.MILE -> numIn * 0.0001893939
                    MeasureUnit.YARD -> numIn * (1.0/3.0)
                    MeasureUnit.INCH -> numIn * 12.0
                    else -> numIn
                }
            }
            MeasureUnit.KILOMETER ->{
                when(unitOut){
                    MeasureUnit.FOOT -> numIn * 3280.839895
                    MeasureUnit.METER -> numIn * 1000.0
                    MeasureUnit.CENTIMETER  -> numIn * 100000.0
                    MeasureUnit.MILLIMETER -> numIn * 1e+6
                    MeasureUnit.MILE -> numIn * 0.6213711922
                    MeasureUnit.YARD -> numIn * 1093.6132983
                    MeasureUnit.INCH -> numIn * 39370.07874
                    else -> numIn
                }
            }
            MeasureUnit.METER ->{
                when(unitOut){
                    MeasureUnit.FOOT -> numIn * 3.280839895
                    MeasureUnit.KILOMETER -> numIn * 0.001
                    MeasureUnit.CENTIMETER  -> numIn * 100.0
                    MeasureUnit.MILLIMETER -> numIn * 1000.0
                    MeasureUnit.MILE -> numIn * 0.0006213712
                    MeasureUnit.YARD -> numIn * 1.0936132983
                    MeasureUnit.INCH -> numIn * 39.37007874
                    else -> numIn
                }
            }
            MeasureUnit.CENTIMETER ->{
                when(unitOut){
                    MeasureUnit.KILOMETER -> numIn * 0.00001
                    MeasureUnit.METER  -> numIn * 0.01
                    MeasureUnit.MILLIMETER -> numIn * 10.0
                    MeasureUnit.MILE -> numIn * 0.0000062137
                    MeasureUnit.YARD -> numIn * 0.010936133
                    MeasureUnit.FOOT -> numIn * 0.032808399
                    MeasureUnit.INCH -> numIn * 0.3937007874
                    else -> numIn
                }
            }
            MeasureUnit.MILLIMETER ->{
                when(unitOut){
                    MeasureUnit.KILOMETER -> numIn * 0.000001
                    MeasureUnit.METER  -> numIn * 0.001
                    MeasureUnit.CENTIMETER -> numIn * 0.1
                    MeasureUnit.MILE -> numIn * 6.213711922E-7
                    MeasureUnit.YARD -> numIn * 0.0010936133
                    MeasureUnit.FOOT -> numIn * 0.0032808399
                    MeasureUnit.INCH -> numIn * 0.0393700787
                    else -> numIn
                }
            }
            MeasureUnit.MILE ->{
                when(unitOut){
                    MeasureUnit.KILOMETER -> numIn * 1.609344
                    MeasureUnit.METER  -> numIn * 1609.344
                    MeasureUnit.CENTIMETER -> numIn * 160934.4
                    MeasureUnit.MILLIMETER -> numIn * 1609344
                    MeasureUnit.YARD -> numIn * 1760.0
                    MeasureUnit.FOOT -> numIn * 5280.0
                    MeasureUnit.INCH -> numIn * 63360.0
                    else -> numIn
                }
            }
            MeasureUnit.YARD ->{
                when(unitOut){
                    MeasureUnit.KILOMETER -> numIn * 0.0009144
                    MeasureUnit.METER  -> numIn * 0.9144
                    MeasureUnit.CENTIMETER -> numIn * 91.44
                    MeasureUnit.MILLIMETER -> numIn * 914.4
                    MeasureUnit.MILE -> numIn * 0.0005681818
                    MeasureUnit.FOOT -> numIn * 3.0
                    MeasureUnit.INCH -> numIn * 36.0
                    else -> numIn
                }
            }
            MeasureUnit.INCH ->{
                when(unitOut){
                    MeasureUnit.KILOMETER -> numIn * 0.0000254
                    MeasureUnit.METER  -> numIn * 0.0254
                    MeasureUnit.CENTIMETER -> numIn * 2.54
                    MeasureUnit.MILLIMETER -> numIn * 25.4
                    MeasureUnit.MILE -> numIn * 0.0000157828
                    MeasureUnit.YARD -> numIn / 36.0
                    MeasureUnit.FOOT -> numIn / 12.0
                    else -> numIn
                }
            }
            else  -> numIn
        }

    }
    private fun convertWeights(numIn:Double, unitIn: MeasureUnit, unitOut: MeasureUnit) : Double{
        return when(unitIn){
            MeasureUnit.METRIC_TON -> {
                when (unitOut) {
                    MeasureUnit.KILOGRAM -> numIn * 1000.0
                    MeasureUnit.GRAM -> numIn * 1000000.0
                    MeasureUnit.MILLIGRAM -> numIn * 1000000000
                    MeasureUnit.STONE -> numIn * 157.47304442
                    MeasureUnit.POUND -> numIn * 2204.6226218
                    MeasureUnit.OUNCE -> numIn * 35273.96195
                    else -> numIn
                }
            }
            MeasureUnit.KILOGRAM -> {
                when (unitOut) {
                    MeasureUnit.METRIC_TON -> numIn * 0.001
                    MeasureUnit.GRAM -> numIn * 1000.0
                    MeasureUnit.MILLIGRAM -> numIn * 1000000
                    MeasureUnit.STONE -> numIn * 0.1574730444
                    MeasureUnit.POUND -> numIn * 2.2046226218
                    MeasureUnit.OUNCE -> numIn * 35.27396195
                    else -> numIn
                }
            }
            MeasureUnit.GRAM -> {
                when (unitOut) {
                    MeasureUnit.METRIC_TON  -> numIn * 0.000001
                    MeasureUnit.KILOGRAM -> numIn * 0.001
                    MeasureUnit.MILLIGRAM -> numIn * 1000.0
                    MeasureUnit.STONE -> numIn * 0.000157473
                    MeasureUnit.POUND -> numIn * 0.0022046226
                    MeasureUnit.OUNCE -> numIn * 0.0352739619
                    else -> numIn
                }
            }
            MeasureUnit.MILLIGRAM -> {
                when (unitOut) {
                    MeasureUnit.METRIC_TON -> numIn * 1.0E-9
                    MeasureUnit.KILOGRAM -> numIn * 0.000001
                    MeasureUnit.GRAM -> numIn * 0.001
                    MeasureUnit.STONE -> numIn * 1.574730444E-7
                    MeasureUnit.POUND -> numIn * 0.0000022046
                    MeasureUnit.OUNCE -> numIn * 0.000035274
                    else -> numIn
                }
            }

            /*UnitType.UNIT_TYPE_WEIGHT_TON_UK -> {
                when (unitOut) {
                    MeasureUnit.METRIC_TON -> numIn * 1.0160469088
                    MeasureUnit.KILOGRAM -> numIn * 1016.0469088
                    MeasureUnit.GRAM -> numIn * 1016046.9088
                    MeasureUnit.MILLIGRAM -> numIn * 1016046908.8
                    UnitType.UNIT_TYPE_WEIGHT_TON_US -> numIn * 1.12
                    MeasureUnit.STONE -> numIn * 160.0
                    MeasureUnit.POUND -> numIn * 2240.0
                    MeasureUnit.OUNCE -> numIn * 35840.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_TON_US -> {
                when (unitOut) {
                    MeasureUnit.METRIC_TON -> numIn * 0.90718474
                    MeasureUnit.KILOGRAM -> numIn * 907.18474
                    MeasureUnit.GRAM -> numIn * 907184.74
                    MeasureUnit.MILLIGRAM -> numIn * 907184740
                    UnitType.UNIT_TYPE_WEIGHT_TON_UK -> numIn * 0.8928571429
                    MeasureUnit.STONE -> numIn * 142.85714286
                    MeasureUnit.POUND -> numIn * 2000.0
                    MeasureUnit.OUNCE -> numIn * 32000.0
                    else -> numIn
                }
            }*/
            MeasureUnit.STONE -> {
                when (unitOut) {
                    MeasureUnit.METRIC_TON -> numIn * 0.0063502932
                    MeasureUnit.KILOGRAM -> numIn * 6.35029318
                    MeasureUnit.GRAM -> numIn * 6350.29318
                    MeasureUnit.MILLIGRAM -> numIn * 6350293.18
                    MeasureUnit.POUND -> numIn * 14.0
                    MeasureUnit.OUNCE -> numIn * 224.0
                    else -> numIn
                }
            }
            MeasureUnit.POUND -> {
                when (unitOut) {
                    MeasureUnit.METRIC_TON -> numIn * 0.0004535924
                    MeasureUnit.KILOGRAM -> numIn * 0.45359237
                    MeasureUnit.GRAM -> numIn * 453.59237
                    MeasureUnit.MILLIGRAM -> numIn * 453592.37
                    MeasureUnit.STONE -> numIn * 0.0714285714
                    MeasureUnit.OUNCE -> numIn * 16.0
                    else -> numIn
                }
            }
            MeasureUnit.OUNCE -> {
                when (unitOut) {
                    MeasureUnit.METRIC_TON -> numIn * 0.0000283495
                    MeasureUnit.KILOGRAM -> numIn * 0.0283495231
                    MeasureUnit.GRAM -> numIn * 28.349523125
                    MeasureUnit.MILLIGRAM -> numIn * 28349.523125
                    MeasureUnit.STONE -> numIn * 0.0044642857
                    MeasureUnit.POUND -> numIn / 16.0 // ok
                    else -> numIn
                }
            }

            else -> numIn
        }
    }
    private fun convertSpeed(numIn:Double, unitIn: MeasureUnit, unitOut: MeasureUnit):Double{
        return when(unitIn){
            MeasureUnit.MILE_PER_HOUR -> {
                when (unitOut) {
                    MeasureUnit.METER_PER_SECOND -> numIn * 0.44704
                    MeasureUnit.KILOMETER_PER_HOUR -> numIn * 1.60934
                    MeasureUnit.KNOT -> numIn * 0.8689762419
                    else -> numIn
                }
            }
            MeasureUnit.METER_PER_SECOND -> {
                when (unitOut) {
                    MeasureUnit.MILE_PER_HOUR -> numIn * 2.2369362921
                    MeasureUnit.KILOMETER_PER_HOUR -> numIn * 3.6
                    MeasureUnit.KNOT -> numIn * 1.9438444924
                    else -> numIn
                }
            }
            MeasureUnit.KILOMETER_PER_HOUR -> {
                when (unitOut) {
                    MeasureUnit.MILE_PER_HOUR -> numIn * 0.6213711922
                    MeasureUnit.METER_PER_SECOND -> numIn * 0.2777777778
                    MeasureUnit.KNOT -> numIn * 0.5399568035
                    else -> numIn
                }
            }
            MeasureUnit.KNOT -> {
                when (unitOut) {
                    MeasureUnit.MILE_PER_HOUR -> numIn * 1.150779448
                    MeasureUnit.METER_PER_SECOND -> numIn * 0.5144444444
                    MeasureUnit.KILOMETER_PER_HOUR -> numIn * 1.852
                    else -> numIn
                }
            }
            else -> numIn
        }
    }
    private fun convertTemperature(numIn:Double, unitIn: MeasureUnit, unitOut: MeasureUnit):Double {
        return when (unitIn) {
            MeasureUnit.FAHRENHEIT -> {
                when (unitOut) {
                    MeasureUnit.CELSIUS -> fToC(numIn)
                    MeasureUnit.KELVIN -> fToK(numIn)
                    else -> numIn
                }
            }
            MeasureUnit.CELSIUS -> {
                when (unitOut) {
                    MeasureUnit.FAHRENHEIT -> cToF(numIn)
                    MeasureUnit.KELVIN -> cToK(numIn)
                    else -> numIn
                }
            }
            MeasureUnit.KELVIN -> {
                when (unitOut) {
                    MeasureUnit.FAHRENHEIT -> kToF(numIn)
                    MeasureUnit.CELSIUS -> kToC(numIn)
                    else -> numIn
                }
            }

            else -> numIn
        }
    }
    private fun convertArea(numIn:Double, unitIn: MeasureUnit, unitOut: MeasureUnit):Double{
        return when(unitIn){
            MeasureUnit.ACRE ->{
                when(unitOut){
                    MeasureUnit.HECTARE -> numIn / 2.4710538147
                    MeasureUnit.SQUARE_METER -> numIn * 4046.86
                    MeasureUnit.SQUARE_KILOMETER -> numIn / 247.1
                    MeasureUnit.SQUARE_FOOT -> numIn * 43560.0
                    MeasureUnit.SQUARE_INCH -> numIn * 6272640.0
                    MeasureUnit.SQUARE_YARD -> numIn * 4840.0
                    MeasureUnit.SQUARE_MILE -> numIn / 640.0
                    else -> numIn
                }
            }
            MeasureUnit.HECTARE ->{
                when(unitOut){
                    MeasureUnit.ACRE -> numIn * 2.4710538147
                    MeasureUnit.SQUARE_METER -> numIn * 10000
                    MeasureUnit.SQUARE_KILOMETER -> numIn / 100
                    MeasureUnit.SQUARE_FOOT -> numIn * 107639.10417
                    MeasureUnit.SQUARE_INCH -> numIn * 15500031
                    MeasureUnit.SQUARE_YARD -> numIn * 11959.900463
                    MeasureUnit.SQUARE_MILE -> numIn / 259
                    else -> numIn
                }
            }

            MeasureUnit.SQUARE_METER ->{
                when(unitOut){
                    MeasureUnit.ACRE -> numIn / 4046.8564224
                    MeasureUnit.HECTARE -> numIn / 10000
                    MeasureUnit.SQUARE_KILOMETER -> numIn / 1000000
                    MeasureUnit.SQUARE_FOOT -> numIn * 10.763910417
                    MeasureUnit.SQUARE_INCH -> numIn * 1550.0031
                    MeasureUnit.SQUARE_YARD -> numIn * 1.19599
                    MeasureUnit.SQUARE_MILE -> numIn / 2589998.4703
                    else -> numIn
                }
            }

            MeasureUnit.SQUARE_KILOMETER ->{
                when(unitOut){
                    MeasureUnit.ACRE -> numIn * 247.10538147
                    MeasureUnit.HECTARE -> numIn * 100
                    MeasureUnit.SQUARE_METER -> numIn * 1000000
                    MeasureUnit.SQUARE_FOOT -> numIn * 10763910.4167
                    MeasureUnit.SQUARE_INCH -> numIn * 1550003100
                    MeasureUnit.SQUARE_YARD -> numIn * 1195990.0463
                    MeasureUnit.SQUARE_MILE -> numIn / 2.59
                    else -> numIn
                }
            }
            MeasureUnit.SQUARE_FOOT -> {
                when (unitOut) {
                    MeasureUnit.ACRE -> numIn / 43560
                    MeasureUnit.HECTARE -> numIn / 107639.10417
                    MeasureUnit.SQUARE_METER -> numIn / 10.764
                    MeasureUnit.SQUARE_KILOMETER -> numIn / 10763910.4167
                    MeasureUnit.SQUARE_INCH -> numIn * 144
                    MeasureUnit.SQUARE_YARD -> numIn / 9
                    MeasureUnit.SQUARE_MILE -> numIn / 27878400
                    else -> numIn
                }
            }
            MeasureUnit.SQUARE_INCH ->{
                when(unitOut){
                    MeasureUnit.ACRE -> numIn / 6272640
                    MeasureUnit.HECTARE -> numIn / 15500031
                    MeasureUnit.SQUARE_METER -> numIn / 1550.0031
                    MeasureUnit.SQUARE_KILOMETER -> numIn / 1550003100
                    MeasureUnit.SQUARE_FOOT -> numIn / 144
                    MeasureUnit.SQUARE_YARD -> numIn / 1296
                    MeasureUnit.SQUARE_MILE -> numIn / 4014489600
                    else -> numIn
                }
            }
            MeasureUnit.SQUARE_YARD ->{
                when(unitOut){
                    MeasureUnit.ACRE -> numIn / 4840
                    MeasureUnit.HECTARE -> numIn / 11960
                    MeasureUnit.SQUARE_METER -> numIn / 1.19599
                    MeasureUnit.SQUARE_KILOMETER -> numIn / 1195990.0463
                    MeasureUnit.SQUARE_FOOT -> numIn * 9
                    MeasureUnit.SQUARE_INCH -> numIn * 1296
                    MeasureUnit.SQUARE_MILE -> numIn / 3097600
                    else -> numIn
                }
            }
            MeasureUnit.SQUARE_MILE ->{
                when(unitOut) {
                    MeasureUnit.ACRE -> numIn * 640
                    MeasureUnit.HECTARE -> numIn * 259
                    MeasureUnit.SQUARE_METER -> numIn * 2589988.1103
                    MeasureUnit.SQUARE_KILOMETER -> numIn * 2.59
                    MeasureUnit.SQUARE_FOOT -> numIn * 27878400
                    MeasureUnit.SQUARE_INCH -> numIn * 4014489600
                    MeasureUnit.SQUARE_YARD -> numIn * 3097600
                    else -> numIn
                }
            }
            else -> numIn
        }
    }

    @Stable
    fun convertUnits(maxDigits: Int, numIn:Double, unitIn: MeasureUnit, unitOut: MeasureUnit):String{
        return formatNumber(maxDigits, convertUnitsRaw(numIn, unitIn, unitOut))
    }
    fun convertUnitsRaw(numIn:Double, unitIn: MeasureUnit, unitOut: MeasureUnit):Double{
        return if(ConverterType.TIME.units.contains(unitIn)){
            convertTimeRaw(numIn,unitIn, unitOut)
        } else if(ConverterType.LENGTH.units.contains(unitIn)){
            convertLength(numIn, unitIn, unitOut)
        } else if(ConverterType.WEIGHT.units.contains(unitIn)){
            convertWeights(numIn, unitIn, unitOut)
        } else if(ConverterType.SPEED.units.contains(unitIn)){
            convertSpeed(numIn, unitIn, unitOut)
        } else if(ConverterType.AREA.units.contains(unitIn)){
            convertArea(numIn, unitIn, unitOut)
        } else {
            convertTemperature(numIn,unitIn, unitOut)
        }
    }
    @Stable
    fun formatNumber(maxDigits:Int, d:Double):String{
        val bigDecimal = BigDecimal.valueOf(d)
        val s = bigDecimal.setScale(maxDigits.coerceAtMost(calcDecimalLen(d)), RoundingMode.HALF_UP)
        return s.stripTrailingZeros().toPlainString()
    }
    private fun calcDecimalLen(d:Double):Int{
        val text = abs(d).toString()
        val integerPlaces = text.indexOf('.')
        return text.length - integerPlaces - 1
    }
}