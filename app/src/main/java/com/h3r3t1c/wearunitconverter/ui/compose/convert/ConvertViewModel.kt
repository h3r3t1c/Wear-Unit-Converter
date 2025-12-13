package com.h3r3t1c.wearunitconverter.ui.compose.convert

import android.content.Context
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
import com.h3r3t1c.wearunitconverter.util.TypeUnit

class ConvertViewModel(context: Context, val type: ConverterType, number: String, mFirstUnit: TypeUnit, var secondUnit: TypeUnit): ViewModel() {

    var dialogState by mutableStateOf(ConvertDialogState.NONE)
    var topValue by mutableDoubleStateOf(number.toDouble())
    var bottomValue by mutableDoubleStateOf(0.0)
    var topUnitString by mutableStateOf("")
    var bottomUnitString by mutableStateOf("")
    var firstUnit by mutableStateOf(mFirstUnit)
    val maxSigDigits = AppPrefs.getMaxSigDigits(context)

    init {
        bottomUnitString = TypeUnit.unitToString(secondUnit)
        updateFirstUnit(firstUnit)
    }

    fun updateFirstUnit(unit: TypeUnit) {
        dialogState = ConvertDialogState.NONE
        if(secondUnit == unit) {
            secondUnit = firstUnit
            firstUnit = unit
            bottomUnitString = TypeUnit.unitToString(secondUnit)
            topUnitString = TypeUnit.unitToString(firstUnit)
            val tmp = bottomValue
            bottomValue = topValue
            topValue = tmp
        }else {
            firstUnit = unit
            topUnitString = TypeUnit.unitToString(unit)
            bottomValue = ConvertHelper.convertUnitsRaw(topValue, firstUnit, secondUnit)
        }
    }
    fun updateSecondUnit(unit: TypeUnit){
        dialogState = ConvertDialogState.NONE
        if(firstUnit == unit) {
            firstUnit = secondUnit
            secondUnit = unit
            bottomUnitString = TypeUnit.unitToString(secondUnit)
            topUnitString = TypeUnit.unitToString(firstUnit)
            val tmp = topValue
            topValue = bottomValue
            bottomValue = tmp
        }else {
            secondUnit = unit
            bottomUnitString = TypeUnit.unitToString(unit)
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
        fun getFactory(context: Context, type: String, number: String, firstUnit: String, secondUnit: String): ViewModelProvider.Factory{
            val factory : ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    val type = ConverterType.valueOf(type)
                    ConvertViewModel(context, type, number, TypeUnit.valueOf(firstUnit), TypeUnit.valueOf(secondUnit))
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