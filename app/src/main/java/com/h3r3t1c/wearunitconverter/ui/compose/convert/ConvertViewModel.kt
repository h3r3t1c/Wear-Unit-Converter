package com.h3r3t1c.wearunitconverter.ui.compose.convert

import android.content.Context
import android.icu.util.MeasureUnit
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.h3r3t1c.wearunitconverter.util.AppPrefs
import com.h3r3t1c.wearunitconverter.util.ConvertHelper
import com.h3r3t1c.wearunitconverter.util.ConverterType
import com.h3r3t1c.wearunitconverter.util.UnitHelper

class ConvertViewModel(context: Context, val type: ConverterType, number: String, mFirstUnit: MeasureUnit, var secondUnit: MeasureUnit): ViewModel() {

    var dialogState by mutableStateOf(ConvertDialogState.NONE)
    var topValue by mutableDoubleStateOf(number.toDouble())
    var bottomValue by mutableDoubleStateOf(0.0)
    var topUnitString by mutableStateOf("")
    var bottomUnitString by mutableStateOf("")
    var firstUnit by mutableStateOf(mFirstUnit)
    val maxSigDigits = AppPrefs.getMaxSigDigits(context)

    init {
        bottomUnitString = UnitHelper.unitToString(secondUnit)
        updateFirstUnit(firstUnit)
    }

    fun updateFirstUnit(unit: MeasureUnit) {
        dialogState = ConvertDialogState.NONE
        if(secondUnit == unit) {
            secondUnit = firstUnit
            firstUnit = unit
            bottomUnitString = UnitHelper.unitToString(secondUnit)
            topUnitString = UnitHelper.unitToString(firstUnit)
            val tmp = bottomValue
            bottomValue = topValue
            topValue = tmp
        }else {
            firstUnit = unit
            topUnitString = UnitHelper.unitToString(unit)
            bottomValue = ConvertHelper.convertUnitsRaw(topValue, firstUnit, secondUnit)
        }
    }
    fun updateSecondUnit(unit: MeasureUnit){
        dialogState = ConvertDialogState.NONE
        if(firstUnit == unit) {
            firstUnit = secondUnit
            secondUnit = unit
            bottomUnitString = UnitHelper.unitToString(secondUnit)
            topUnitString = UnitHelper.unitToString(firstUnit)
            val tmp = topValue
            topValue = bottomValue
            bottomValue = tmp
        }else {
            secondUnit = unit
            bottomUnitString = UnitHelper.unitToString(unit)
            topValue = ConvertHelper.convertUnitsRaw(bottomValue, secondUnit, firstUnit)
        }
    }
    fun updateFirstValue(value: Double) {
        dialogState = ConvertDialogState.NONE
        topValue = value
        bottomValue = ConvertHelper.convertUnitsRaw(topValue, firstUnit, secondUnit)
    }
    fun updateSecondValue(value: Double){
        dialogState = ConvertDialogState.NONE
        bottomValue = value
        topValue = ConvertHelper.convertUnitsRaw(bottomValue, secondUnit, firstUnit)
    }
    companion object{
        fun getFactory(context: Context, type: String, number: String, firstUnit: Int, secondUnit: Int): ViewModelProvider.Factory{
            val factory : ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    val type = ConverterType.valueOf(type)
                    ConvertViewModel(context, type, number, type.units[firstUnit], type.units[secondUnit])
                }
            }
            return factory
        }
    }
}
enum class ConvertDialogState{
    NONE,
    CHANGE_FIRST_UNIT,
    CHANGE_SECOND_UNIT,
    CHANGE_FIRST_VALUE,
    CHANGE_SECOND_VALUE,
}