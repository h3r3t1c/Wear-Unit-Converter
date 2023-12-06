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
    }
    @Test
    fun testLengthFormulas(){
        // Foot to
        checkMath(1.0, UnitType.UNIT_TYPE_LENGTH_FOOT, UnitType.UNIT_TYPE_LENGTH_KILOMETER, 0.0003048)
    }

    private fun checkMath(numIn:Double, unitIn:Int, unitOut:Int, expectedValue:Double){
        val decimalLen = calcDecimalLen(expectedValue)
        val expectedValueBigDecimal = BigDecimal(expectedValue).setScale(decimalLen, RoundingMode.HALF_UP)
        val outNum = BigDecimal(ConvertHelper.convertUnitsRaw(numIn, unitIn, unitOut)).setScale(decimalLen, RoundingMode.HALF_UP);

        assert(outNum.equals(expectedValueBigDecimal)){
            "From $numIn :: expected: $expectedValueBigDecimal, got:$outNum"
        }

    }
    private fun calcDecimalLen(d:Double):Int{
        val text = abs(d).toString()
        val integerPlaces = text.indexOf('.')
        return text.length - integerPlaces - 1
    }
}