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
import com.h3r3t1c.wearunitconverter.util.ConvertHelper
import com.h3r3t1c.wearunitconverter.util.ConverterType
import com.h3r3t1c.wearunitconverter.util.UnitType

class ConvertViewModel(context: Context, val type: ConverterType, number: String, mFirstUnit: Int, var secondUnit: Int): ViewModel() {

    var dialogState by mutableStateOf(ConvertDialogState.NONE)
    var topValue by mutableDoubleStateOf(number.toDouble())
    var bottomValue by mutableDoubleStateOf(0.0)
    var topUnitString by mutableStateOf("")
    var bottomUnitString by mutableStateOf("")
    var firstUnit by mutableStateOf(mFirstUnit)

    init {
        bottomUnitString = UnitType.unitTypeToString(secondUnit)
        updateFirstUnit(firstUnit)
    }

    fun updateFirstUnit(unit: Int) {
        dialogState = ConvertDialogState.NONE
        if(secondUnit == unit) {
            secondUnit = firstUnit
            bottomUnitString = UnitType.unitTypeToString(secondUnit)
        }
        firstUnit = unit
        topUnitString = UnitType.unitTypeToString(unit)
        bottomValue = ConvertHelper.convertUnitsRaw(topValue, firstUnit, secondUnit)

    }
    fun updateSecondUnit(unit: Int){
        if(firstUnit == unit) {
            firstUnit = secondUnit
            topUnitString = UnitType.unitTypeToString(firstUnit)
        }
        dialogState = ConvertDialogState.NONE
        secondUnit = unit
        bottomUnitString = UnitType.unitTypeToString(unit)
        topValue = ConvertHelper.convertUnitsRaw(bottomValue, secondUnit, firstUnit)
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
                    ConvertViewModel(context, ConverterType.valueOf(type), number, firstUnit, secondUnit)
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