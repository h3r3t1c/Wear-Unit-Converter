package com.h3r3t1c.wearunitconverter.util


import androidx.compose.runtime.Stable
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

object ConvertHelper {


    private val supportedTimedUnits = listOf(
        DurationUnit.MILLISECONDS to TypeUnit.MILLISECOND,
        DurationUnit.SECONDS to TypeUnit.SECOND,
        DurationUnit.MINUTES to TypeUnit.MINUTE,
        DurationUnit.HOURS to TypeUnit.HOUR,
        DurationUnit.DAYS to TypeUnit.DAY
    )

    private fun fToC(f:Double):Double = (f-32.0)*(0.55555555556)
    private fun fToK(f:Double):Double = fToC(f)+273.15

    private fun cToF(c:Double):Double = (c*(1.8))+32.0
    private fun cToK(c:Double):Double = c+273.15

    private fun kToF(k:Double):Double = kToC(k)*(1.8)+32.0
    private fun kToC(k:Double):Double = k-273.15


    @OptIn(ExperimentalTime::class)
    fun convertTimeRaw(value:Double, fromUnit: TypeUnit, toUnit: TypeUnit):Double{
        val first = supportedTimedUnits.find { it.second == fromUnit }
        val second = supportedTimedUnits.find { it.second == toUnit }
        return Duration.convert(value, first!!.first, second!!.first)
    }
    private fun convertLength(numIn:Double, unitIn: TypeUnit, unitOut: TypeUnit): Double{
        return when(unitIn){
            TypeUnit.FOOT ->{
                when(unitOut){
                    TypeUnit.KILOMETER -> numIn * 0.0003048
                    TypeUnit.METER -> numIn * 0.3048
                    TypeUnit.CENTIMETER  -> numIn * 30.48
                    TypeUnit.MILLIMETER -> numIn * 304.8
                    TypeUnit.MILE -> numIn * 0.0001893939
                    TypeUnit.YARD -> numIn * (1.0/3.0)
                    TypeUnit.INCH -> numIn * 12.0
                    else -> numIn
                }
            }
            TypeUnit.KILOMETER ->{
                when(unitOut){
                    TypeUnit.FOOT -> numIn * 3280.839895
                    TypeUnit.METER -> numIn * 1000.0
                    TypeUnit.CENTIMETER  -> numIn * 100000.0
                    TypeUnit.MILLIMETER -> numIn * 1e+6
                    TypeUnit.MILE -> numIn * 0.6213711922
                    TypeUnit.YARD -> numIn * 1093.6132983
                    TypeUnit.INCH -> numIn * 39370.07874
                    else -> numIn
                }
            }
            TypeUnit.METER ->{
                when(unitOut){
                    TypeUnit.FOOT -> numIn * 3.280839895
                    TypeUnit.KILOMETER -> numIn * 0.001
                    TypeUnit.CENTIMETER  -> numIn * 100.0
                    TypeUnit.MILLIMETER -> numIn * 1000.0
                    TypeUnit.MILE -> numIn * 0.0006213712
                    TypeUnit.YARD -> numIn * 1.0936132983
                    TypeUnit.INCH -> numIn * 39.37007874
                    else -> numIn
                }
            }
            TypeUnit.CENTIMETER ->{
                when(unitOut){
                    TypeUnit.KILOMETER -> numIn * 0.00001
                    TypeUnit.METER  -> numIn * 0.01
                    TypeUnit.MILLIMETER -> numIn * 10.0
                    TypeUnit.MILE -> numIn * 0.0000062137
                    TypeUnit.YARD -> numIn * 0.010936133
                    TypeUnit.FOOT -> numIn * 0.032808399
                    TypeUnit.INCH -> numIn * 0.3937007874
                    else -> numIn
                }
            }
            TypeUnit.MILLIMETER ->{
                when(unitOut){
                    TypeUnit.KILOMETER -> numIn * 0.000001
                    TypeUnit.METER  -> numIn * 0.001
                    TypeUnit.CENTIMETER -> numIn * 0.1
                    TypeUnit.MILE -> numIn * 6.213711922E-7
                    TypeUnit.YARD -> numIn * 0.0010936133
                    TypeUnit.FOOT -> numIn * 0.0032808399
                    TypeUnit.INCH -> numIn * 0.0393700787
                    else -> numIn
                }
            }
            TypeUnit.MILE ->{
                when(unitOut){
                    TypeUnit.KILOMETER -> numIn * 1.609344
                    TypeUnit.METER  -> numIn * 1609.344
                    TypeUnit.CENTIMETER -> numIn * 160934.4
                    TypeUnit.MILLIMETER -> numIn * 1609344
                    TypeUnit.YARD -> numIn * 1760.0
                    TypeUnit.FOOT -> numIn * 5280.0
                    TypeUnit.INCH -> numIn * 63360.0
                    else -> numIn
                }
            }
            TypeUnit.YARD ->{
                when(unitOut){
                    TypeUnit.KILOMETER -> numIn * 0.0009144
                    TypeUnit.METER  -> numIn * 0.9144
                    TypeUnit.CENTIMETER -> numIn * 91.44
                    TypeUnit.MILLIMETER -> numIn * 914.4
                    TypeUnit.MILE -> numIn * 0.0005681818
                    TypeUnit.FOOT -> numIn * 3.0
                    TypeUnit.INCH -> numIn * 36.0
                    else -> numIn
                }
            }
            TypeUnit.INCH ->{
                when(unitOut){
                    TypeUnit.KILOMETER -> numIn * 0.0000254
                    TypeUnit.METER  -> numIn * 0.0254
                    TypeUnit.CENTIMETER -> numIn * 2.54
                    TypeUnit.MILLIMETER -> numIn * 25.4
                    TypeUnit.MILE -> numIn * 0.0000157828
                    TypeUnit.YARD -> numIn / 36.0
                    TypeUnit.FOOT -> numIn / 12.0
                    else -> numIn
                }
            }
            else  -> numIn
        }

    }
    private fun convertWeights(numIn:Double, unitIn: TypeUnit, unitOut: TypeUnit) : Double{
        return when(unitIn){
            TypeUnit.METRIC_TON -> {
                when (unitOut) {
                    TypeUnit.KILOGRAM -> numIn * 1000.0
                    TypeUnit.GRAM -> numIn * 1000000.0
                    TypeUnit.MILLIGRAM -> numIn * 1000000000
                    TypeUnit.STONE -> numIn * 157.47304442
                    TypeUnit.POUND -> numIn * 2204.6226218
                    TypeUnit.OUNCE -> numIn * 35273.96195
                    else -> numIn
                }
            }
            TypeUnit.KILOGRAM -> {
                when (unitOut) {
                    TypeUnit.METRIC_TON -> numIn * 0.001
                    TypeUnit.GRAM -> numIn * 1000.0
                    TypeUnit.MILLIGRAM -> numIn * 1000000
                    TypeUnit.STONE -> numIn * 0.1574730444
                    TypeUnit.POUND -> numIn * 2.2046226218
                    TypeUnit.OUNCE -> numIn * 35.27396195
                    else -> numIn
                }
            }
            TypeUnit.GRAM -> {
                when (unitOut) {
                    TypeUnit.METRIC_TON  -> numIn * 0.000001
                    TypeUnit.KILOGRAM -> numIn * 0.001
                    TypeUnit.MILLIGRAM -> numIn * 1000.0
                    TypeUnit.STONE -> numIn * 0.000157473
                    TypeUnit.POUND -> numIn * 0.0022046226
                    TypeUnit.OUNCE -> numIn * 0.0352739619
                    else -> numIn
                }
            }
            TypeUnit.MILLIGRAM -> {
                when (unitOut) {
                    TypeUnit.METRIC_TON -> numIn * 1.0E-9
                    TypeUnit.KILOGRAM -> numIn * 0.000001
                    TypeUnit.GRAM -> numIn * 0.001
                    TypeUnit.STONE -> numIn * 1.574730444E-7
                    TypeUnit.POUND -> numIn * 0.0000022046
                    TypeUnit.OUNCE -> numIn * 0.000035274
                    else -> numIn
                }
            }

            /*UnitType.UNIT_TYPE_WEIGHT_TON_UK -> {
                when (unitOut) {
                    TypeUnit.METRIC_TON -> numIn * 1.0160469088
                    TypeUnit.KILOGRAM -> numIn * 1016.0469088
                    TypeUnit.GRAM -> numIn * 1016046.9088
                    TypeUnit.MILLIGRAM -> numIn * 1016046908.8
                    UnitType.UNIT_TYPE_WEIGHT_TON_US -> numIn * 1.12
                    TypeUnit.STONE -> numIn * 160.0
                    TypeUnit.POUND -> numIn * 2240.0
                    TypeUnit.OUNCE -> numIn * 35840.0
                    else -> numIn
                }
            }

            UnitType.UNIT_TYPE_WEIGHT_TON_US -> {
                when (unitOut) {
                    TypeUnit.METRIC_TON -> numIn * 0.90718474
                    TypeUnit.KILOGRAM -> numIn * 907.18474
                    TypeUnit.GRAM -> numIn * 907184.74
                    TypeUnit.MILLIGRAM -> numIn * 907184740
                    UnitType.UNIT_TYPE_WEIGHT_TON_UK -> numIn * 0.8928571429
                    TypeUnit.STONE -> numIn * 142.85714286
                    TypeUnit.POUND -> numIn * 2000.0
                    TypeUnit.OUNCE -> numIn * 32000.0
                    else -> numIn
                }
            }*/
            TypeUnit.STONE -> {
                when (unitOut) {
                    TypeUnit.METRIC_TON -> numIn * 0.0063502932
                    TypeUnit.KILOGRAM -> numIn * 6.35029318
                    TypeUnit.GRAM -> numIn * 6350.29318
                    TypeUnit.MILLIGRAM -> numIn * 6350293.18
                    TypeUnit.POUND -> numIn * 14.0
                    TypeUnit.OUNCE -> numIn * 224.0
                    else -> numIn
                }
            }
            TypeUnit.POUND -> {
                when (unitOut) {
                    TypeUnit.METRIC_TON -> numIn * 0.0004535924
                    TypeUnit.KILOGRAM -> numIn * 0.45359237
                    TypeUnit.GRAM -> numIn * 453.59237
                    TypeUnit.MILLIGRAM -> numIn * 453592.37
                    TypeUnit.STONE -> numIn * 0.0714285714
                    TypeUnit.OUNCE -> numIn * 16.0
                    else -> numIn
                }
            }
            TypeUnit.OUNCE -> {
                when (unitOut) {
                    TypeUnit.METRIC_TON -> numIn * 0.0000283495
                    TypeUnit.KILOGRAM -> numIn * 0.0283495231
                    TypeUnit.GRAM -> numIn * 28.349523125
                    TypeUnit.MILLIGRAM -> numIn * 28349.523125
                    TypeUnit.STONE -> numIn * 0.0044642857
                    TypeUnit.POUND -> numIn / 16.0 // ok
                    else -> numIn
                }
            }

            else -> numIn
        }
    }
    private fun convertSpeed(numIn:Double, unitIn: TypeUnit, unitOut: TypeUnit):Double{
        return when(unitIn){
            TypeUnit.MILE_PER_HOUR -> {
                when (unitOut) {
                    TypeUnit.METER_PER_SECOND -> numIn * 0.44704
                    TypeUnit.KILOMETER_PER_HOUR -> numIn * 1.60934
                    TypeUnit.KNOT -> numIn * 0.8689762419
                    else -> numIn
                }
            }
            TypeUnit.METER_PER_SECOND -> {
                when (unitOut) {
                    TypeUnit.MILE_PER_HOUR -> numIn * 2.2369362921
                    TypeUnit.KILOMETER_PER_HOUR -> numIn * 3.6
                    TypeUnit.KNOT -> numIn * 1.9438444924
                    else -> numIn
                }
            }
            TypeUnit.KILOMETER_PER_HOUR -> {
                when (unitOut) {
                    TypeUnit.MILE_PER_HOUR -> numIn * 0.6213711922
                    TypeUnit.METER_PER_SECOND -> numIn * 0.2777777778
                    TypeUnit.KNOT -> numIn * 0.5399568035
                    else -> numIn
                }
            }
            TypeUnit.KNOT -> {
                when (unitOut) {
                    TypeUnit.MILE_PER_HOUR -> numIn * 1.150779448
                    TypeUnit.METER_PER_SECOND -> numIn * 0.5144444444
                    TypeUnit.KILOMETER_PER_HOUR -> numIn * 1.852
                    else -> numIn
                }
            }
            else -> numIn
        }
    }
    private fun convertTemperature(numIn:Double, unitIn: TypeUnit, unitOut: TypeUnit):Double {
        return when (unitIn) {
            TypeUnit.FAHRENHEIT -> {
                when (unitOut) {
                    TypeUnit.CELSIUS -> fToC(numIn)
                    TypeUnit.KELVIN -> fToK(numIn)
                    else -> numIn
                }
            }
            TypeUnit.CELSIUS -> {
                when (unitOut) {
                    TypeUnit.FAHRENHEIT -> cToF(numIn)
                    TypeUnit.KELVIN -> cToK(numIn)
                    else -> numIn
                }
            }
            TypeUnit.KELVIN -> {
                when (unitOut) {
                    TypeUnit.FAHRENHEIT -> kToF(numIn)
                    TypeUnit.CELSIUS -> kToC(numIn)
                    else -> numIn
                }
            }

            else -> numIn
        }
    }
    private fun convertArea(numIn:Double, unitIn: TypeUnit, unitOut: TypeUnit):Double{
        return when(unitIn){
            TypeUnit.ACRE ->{
                when(unitOut){
                    TypeUnit.HECTARE -> numIn / 2.4710538147
                    TypeUnit.SQUARE_METER -> numIn * 4046.86
                    TypeUnit.SQUARE_KILOMETER -> numIn / 247.1
                    TypeUnit.SQUARE_FOOT -> numIn * 43560.0
                    TypeUnit.SQUARE_INCH -> numIn * 6272640.0
                    TypeUnit.SQUARE_YARD -> numIn * 4840.0
                    TypeUnit.SQUARE_MILE -> numIn / 640.0
                    else -> numIn
                }
            }
            TypeUnit.HECTARE ->{
                when(unitOut){
                    TypeUnit.ACRE -> numIn * 2.4710538147
                    TypeUnit.SQUARE_METER -> numIn * 10000
                    TypeUnit.SQUARE_KILOMETER -> numIn / 100
                    TypeUnit.SQUARE_FOOT -> numIn * 107639.10417
                    TypeUnit.SQUARE_INCH -> numIn * 15500031
                    TypeUnit.SQUARE_YARD -> numIn * 11959.900463
                    TypeUnit.SQUARE_MILE -> numIn / 259
                    else -> numIn
                }
            }

            TypeUnit.SQUARE_METER ->{
                when(unitOut){
                    TypeUnit.ACRE -> numIn / 4046.8564224
                    TypeUnit.HECTARE -> numIn / 10000
                    TypeUnit.SQUARE_KILOMETER -> numIn / 1000000
                    TypeUnit.SQUARE_FOOT -> numIn * 10.763910417
                    TypeUnit.SQUARE_INCH -> numIn * 1550.0031
                    TypeUnit.SQUARE_YARD -> numIn * 1.19599
                    TypeUnit.SQUARE_MILE -> numIn / 2589998.4703
                    else -> numIn
                }
            }

            TypeUnit.SQUARE_KILOMETER ->{
                when(unitOut){
                    TypeUnit.ACRE -> numIn * 247.10538147
                    TypeUnit.HECTARE -> numIn * 100
                    TypeUnit.SQUARE_METER -> numIn * 1000000
                    TypeUnit.SQUARE_FOOT -> numIn * 10763910.4167
                    TypeUnit.SQUARE_INCH -> numIn * 1550003100
                    TypeUnit.SQUARE_YARD -> numIn * 1195990.0463
                    TypeUnit.SQUARE_MILE -> numIn / 2.59
                    else -> numIn
                }
            }
            TypeUnit.SQUARE_FOOT -> {
                when (unitOut) {
                    TypeUnit.ACRE -> numIn / 43560
                    TypeUnit.HECTARE -> numIn / 107639.10417
                    TypeUnit.SQUARE_METER -> numIn / 10.764
                    TypeUnit.SQUARE_KILOMETER -> numIn / 10763910.4167
                    TypeUnit.SQUARE_INCH -> numIn * 144
                    TypeUnit.SQUARE_YARD -> numIn / 9
                    TypeUnit.SQUARE_MILE -> numIn / 27878400
                    else -> numIn
                }
            }
            TypeUnit.SQUARE_INCH ->{
                when(unitOut){
                    TypeUnit.ACRE -> numIn / 6272640
                    TypeUnit.HECTARE -> numIn / 15500031
                    TypeUnit.SQUARE_METER -> numIn / 1550.0031
                    TypeUnit.SQUARE_KILOMETER -> numIn / 1550003100
                    TypeUnit.SQUARE_FOOT -> numIn / 144
                    TypeUnit.SQUARE_YARD -> numIn / 1296
                    TypeUnit.SQUARE_MILE -> numIn / 4014489600
                    else -> numIn
                }
            }
            TypeUnit.SQUARE_YARD ->{
                when(unitOut){
                    TypeUnit.ACRE -> numIn / 4840
                    TypeUnit.HECTARE -> numIn / 11960
                    TypeUnit.SQUARE_METER -> numIn / 1.19599
                    TypeUnit.SQUARE_KILOMETER -> numIn / 1195990.0463
                    TypeUnit.SQUARE_FOOT -> numIn * 9
                    TypeUnit.SQUARE_INCH -> numIn * 1296
                    TypeUnit.SQUARE_MILE -> numIn / 3097600
                    else -> numIn
                }
            }
            TypeUnit.SQUARE_MILE ->{
                when(unitOut) {
                    TypeUnit.ACRE -> numIn * 640
                    TypeUnit.HECTARE -> numIn * 259
                    TypeUnit.SQUARE_METER -> numIn * 2589988.1103
                    TypeUnit.SQUARE_KILOMETER -> numIn * 2.59
                    TypeUnit.SQUARE_FOOT -> numIn * 27878400
                    TypeUnit.SQUARE_INCH -> numIn * 4014489600
                    TypeUnit.SQUARE_YARD -> numIn * 3097600
                    else -> numIn
                }
            }
            else -> numIn
        }
    }

    @Stable
    fun convertUnits(maxDigits: Int, numIn:Double, unitIn: TypeUnit, unitOut: TypeUnit):String{
        return formatNumber(maxDigits, convertUnitsRaw(numIn, unitIn, unitOut))
    }
    fun convertUnitsRaw(numIn:Double, unitIn: TypeUnit, unitOut: TypeUnit):Double{
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