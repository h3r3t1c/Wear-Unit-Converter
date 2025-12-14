import android.icu.util.MeasureUnit
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.abs

@RunWith(JUnit4::class)
class UnitFormulaTester {

    @Test
    fun test(){
        val inch = 36.0

    }

 private fun checkMath(numIn:Double, unitIn: MeasureUnit, unitOut: MeasureUnit, expectedValue:Double){
     val decimalLen = calcDecimalLen(expectedValue)
     val expectedValueBigDecimal = BigDecimal(expectedValue).setScale(decimalLen, RoundingMode.HALF_UP)
     val outNum = 0 //BigDecimal(ConvertHelper.convertUnitsRaw(numIn, unitIn, unitOut)).setScale(decimalLen, RoundingMode.HALF_UP)

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