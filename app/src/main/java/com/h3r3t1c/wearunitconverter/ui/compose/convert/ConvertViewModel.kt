package com.h3r3t1c.wearunitconverter.ui.compose.convert

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.h3r3t1c.wearunitconverter.util.AppPrefs
import eu.hansolo.unit.converter.Converter

class ConvertViewModel(context: Context, val type: Converter.Category, number: String, mFirstUnit: Converter.UnitDefinition,  mSecondUnit: Converter.UnitDefinition): ViewModel() {

    var dialogState by mutableStateOf(ConvertDialogState.NONE)
    var firstValue by mutableStateOf(number)
    var secondValue by mutableStateOf("")
    var firstUnit by mutableStateOf(mFirstUnit)
    var secondUnit by mutableStateOf(mSecondUnit)

    val maxSigDigits = AppPrefs.getMaxSigDigits(context)
    private val useEngineerNotation = AppPrefs.getUseEngineerNotation(context)

    val conversions = mutableStateListOf<Pair<String, Converter.UnitDefinition>>()
    val units = Converter.UnitDefinition.entries.filter { it.UNIT.category == type }

    init {
        val converter = Converter(type, firstUnit, maxSigDigits)
        secondValue = converter.convertToString(firstValue.toDouble(), secondUnit, useEngineerNotation)
        updateConversions(converter)
    }

    fun updateFirstUnit(unit: Converter.UnitDefinition) {
        dialogState = ConvertDialogState.NONE
        firstUnit = unit
        val converter = Converter(type, firstUnit, maxSigDigits)
        secondValue = converter.convertToString(firstValue.toDouble(), secondUnit, useEngineerNotation)
        updateConversions(converter)
    }
    fun updateSecondUnit(unit: Converter.UnitDefinition){
        dialogState = ConvertDialogState.NONE
        secondUnit = unit
        val converter = Converter(type, firstUnit, maxSigDigits)
        secondValue = converter.convertToString(firstValue.toDouble(), secondUnit, useEngineerNotation)
        updateConversions(converter)
    }
    fun updateFirstValue(value: String) {
        dialogState = ConvertDialogState.NONE
        firstValue = value
        val converter = Converter(type, firstUnit, maxSigDigits)
        secondValue = converter.convertToString(firstValue.toDouble(), secondUnit, useEngineerNotation)
        updateConversions(converter)
    }
    fun updateSecondValue(value: String){
        dialogState = ConvertDialogState.NONE
        secondValue = value
        val converter = Converter(type, secondUnit, maxSigDigits)
        firstValue = converter.convertToString(secondValue.toDouble(), firstUnit, useEngineerNotation)
        updateConversions(converter)
    }
    private fun updateConversions(converter: Converter){
        conversions.clear()
        units.filter { it != firstUnit}.forEach {
            conversions.add(Pair(converter.convertToString(firstValue.toDouble(), it, useEngineerNotation), it))
        }
    }
    companion object{
        fun getFactory(context: Context, type: String, number: String, firstUnit: String, secondUnit: String): ViewModelProvider.Factory{
            val factory : ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    ConvertViewModel(context, Converter.Category.valueOf(type), number, Converter.UnitDefinition.valueOf(firstUnit), Converter.UnitDefinition.valueOf(secondUnit))
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