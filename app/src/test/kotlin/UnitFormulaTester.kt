import com.h3r3t1c.wearunitconverter.util.ConvertHelper
import com.h3r3t1c.wearunitconverter.util.UnitType

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.abs

@RunWith(JUnit4::class)
class UnitFormulaTester {

    // https://www.unitconverters.net/time-converter.html

    @Test
    fun testTemperatureFormulas(){

        // F to
        checkMath(0.0, UnitType.UNIT_TYPE_TEMPERATURE_FAHRENHEIT, UnitType.UNIT_TYPE_TEMPERATURE_CELSIUS, -17.7777778)
        checkMath(0.0, UnitType.UNIT_TYPE_TEMPERATURE_FAHRENHEIT, UnitType.UNIT_TYPE_TEMPERATURE_KELVIN, 255.372)


        // C to
        checkMath(0.0, UnitType.UNIT_TYPE_TEMPERATURE_CELSIUS, UnitType.UNIT_TYPE_TEMPERATURE_FAHRENHEIT, 32.0)
        checkMath(0.0, UnitType.UNIT_TYPE_TEMPERATURE_CELSIUS, UnitType.UNIT_TYPE_TEMPERATURE_KELVIN, 273.15)

        checkMath(5.5, UnitType.UNIT_TYPE_TEMPERATURE_CELSIUS, UnitType.UNIT_TYPE_TEMPERATURE_FAHRENHEIT, 41.9)
        checkMath(5.5, UnitType.UNIT_TYPE_TEMPERATURE_CELSIUS, UnitType.UNIT_TYPE_TEMPERATURE_KELVIN, 278.65)


        // K to
        checkMath(100.0, UnitType.UNIT_TYPE_TEMPERATURE_KELVIN, UnitType.UNIT_TYPE_TEMPERATURE_FAHRENHEIT, -279.67)
        checkMath(100.0, UnitType.UNIT_TYPE_TEMPERATURE_KELVIN, UnitType.UNIT_TYPE_TEMPERATURE_CELSIUS, -173.15)
    }
    @Test
    fun testSpeedFormulas(){
        // MPH to
        checkMath(1.0, UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR, UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND, 1.46667)
        checkMath(1.0, UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR, UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND, 0.44704)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR, UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND, 0.89408)
        checkMath(1.0, UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR, UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR, 1.60934)
        checkMath(1.0, UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR, UnitType.UNIT_TYPE_SPEED_KNOT, 0.868976)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR, UnitType.UNIT_TYPE_SPEED_KNOT, 1.73795)

        // ft/s
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND, UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR, 1.3636363636)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND, UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND, 0.6096)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND, UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR, 2.19456)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND, UnitType.UNIT_TYPE_SPEED_KNOT, 1.1849676026)

        // m/s
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND, UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR, 4.47387)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND, UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND, 6.56167979)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND, UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR, 7.2)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND, UnitType.UNIT_TYPE_SPEED_KNOT, 3.887689)

        // km/h
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR, UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR, 1.242742384)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR, UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND, 1.8226888306)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR, UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND, 0.5555555556)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR, UnitType.UNIT_TYPE_SPEED_KNOT, 1.07991)

        // knots
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_KNOT, UnitType.UNIT_TYPE_SPEED_MILE_PER_HOUR, 2.30156)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_KNOT, UnitType.UNIT_TYPE_SPEED_FOOT_PER_SECOND, 3.3756)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_KNOT, UnitType.UNIT_TYPE_SPEED_METER_PER_SECOND, 1.028889)
        checkMath(2.0, UnitType.UNIT_TYPE_SPEED_KNOT, UnitType.UNIT_TYPE_SPEED_KM_PER_HOUR, 3.704)
    }
    @Test
    fun testLengthFormulas(){
        // Foot to
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_FOOT, UnitType.UNIT_TYPE_LENGTH_KILOMETER, 0.0006096)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_FOOT, UnitType.UNIT_TYPE_LENGTH_METER, 0.6096)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_FOOT, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, 60.96)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_FOOT, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, 609.6)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_FOOT, UnitType.UNIT_TYPE_LENGTH_MILE, 0.0003787879)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_FOOT, UnitType.UNIT_TYPE_LENGTH_YARD, 0.6666666667)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_FOOT, UnitType.UNIT_TYPE_LENGTH_INCH, 24.0)

        // KM to
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_KILOMETER, UnitType.UNIT_TYPE_LENGTH_FOOT, 6561.67979)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_KILOMETER, UnitType.UNIT_TYPE_LENGTH_METER, 2000.0)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_KILOMETER, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, 200000.0)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_KILOMETER, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, 2000000.0)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_KILOMETER, UnitType.UNIT_TYPE_LENGTH_MILE, 1.2427423844)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_KILOMETER, UnitType.UNIT_TYPE_LENGTH_YARD, 2187.2265966)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_KILOMETER, UnitType.UNIT_TYPE_LENGTH_INCH, 78740.15748)

        // meter to
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_METER, UnitType.UNIT_TYPE_LENGTH_FOOT, 6.56167979)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_METER, UnitType.UNIT_TYPE_LENGTH_KILOMETER, 0.002)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_METER, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, 200.0)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_METER, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, 2000.0)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_METER, UnitType.UNIT_TYPE_LENGTH_MILE, 0.0012427424)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_METER, UnitType.UNIT_TYPE_LENGTH_YARD, 2.1872265966)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_METER, UnitType.UNIT_TYPE_LENGTH_INCH, 78.74015748)

        // cm to
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, UnitType.UNIT_TYPE_LENGTH_FOOT, 0.065616798)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, UnitType.UNIT_TYPE_LENGTH_KILOMETER, 0.00002)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, UnitType.UNIT_TYPE_LENGTH_METER, 0.02)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, 20.0)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, UnitType.UNIT_TYPE_LENGTH_MILE, 0.0000124274)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, UnitType.UNIT_TYPE_LENGTH_YARD, 0.021872266)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, UnitType.UNIT_TYPE_LENGTH_INCH, 0.7874015748)

        // mm to
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, UnitType.UNIT_TYPE_LENGTH_FOOT, 0.0065616798)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, UnitType.UNIT_TYPE_LENGTH_KILOMETER, 0.000002)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, UnitType.UNIT_TYPE_LENGTH_METER, 0.002)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, 0.2)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, UnitType.UNIT_TYPE_LENGTH_MILE, 0.0000012427)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, UnitType.UNIT_TYPE_LENGTH_YARD, 0.0021872266)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, UnitType.UNIT_TYPE_LENGTH_INCH, 0.0787401574)

        // mile to
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILE, UnitType.UNIT_TYPE_LENGTH_FOOT, 10560.0)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILE, UnitType.UNIT_TYPE_LENGTH_KILOMETER, 3.218688)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILE, UnitType.UNIT_TYPE_LENGTH_METER, 3218.688)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILE, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, 321868.8)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILE, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, 3218688.0)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILE, UnitType.UNIT_TYPE_LENGTH_YARD, 3520.0)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_MILE, UnitType.UNIT_TYPE_LENGTH_INCH, 126720.0)

        // yard to
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_YARD, UnitType.UNIT_TYPE_LENGTH_FOOT, 6.0)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_YARD, UnitType.UNIT_TYPE_LENGTH_KILOMETER, 0.0018288)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_YARD, UnitType.UNIT_TYPE_LENGTH_METER, 1.8288)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_YARD, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, 182.88)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_YARD, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, 1828.8)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_YARD, UnitType.UNIT_TYPE_LENGTH_MILE, 0.0011363636)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_YARD, UnitType.UNIT_TYPE_LENGTH_INCH, 72.0)

        // inch to
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_INCH, UnitType.UNIT_TYPE_LENGTH_FOOT, 0.1666666667)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_INCH, UnitType.UNIT_TYPE_LENGTH_KILOMETER, 0.0000508)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_INCH, UnitType.UNIT_TYPE_LENGTH_METER, 0.0508)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_INCH, UnitType.UNIT_TYPE_LENGTH_CENTIMETER, 5.08)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_INCH, UnitType.UNIT_TYPE_LENGTH_MILLIMETER, 50.8)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_INCH, UnitType.UNIT_TYPE_LENGTH_MILE, 0.0000315657)
        checkMath(2.0, UnitType.UNIT_TYPE_LENGTH_INCH, UnitType.UNIT_TYPE_LENGTH_YARD, 0.0555555556)
    }

    @Test
    fun testMassFormulas(){

    }
    private fun checkMath(numIn:Double, unitIn:Int, unitOut:Int, expectedValue:Double){
        val decimalLen = calcDecimalLen(expectedValue)
        val expectedValueBigDecimal = BigDecimal(expectedValue).setScale(decimalLen, RoundingMode.HALF_UP)
        val outNum = BigDecimal(ConvertHelper.convertUnitsRaw(numIn, unitIn, unitOut)).setScale(decimalLen, RoundingMode.HALF_UP)

        assert(outNum.equals(expectedValueBigDecimal)){
            "From $numIn :: expected: $expectedValueBigDecimal, got: $outNum"
        }

    }
    private fun calcDecimalLen(d:Double):Int{
        val text = abs(d).toString()
        val integerPlaces = text.indexOf('.')
        return text.length - integerPlaces - 1
    }
}