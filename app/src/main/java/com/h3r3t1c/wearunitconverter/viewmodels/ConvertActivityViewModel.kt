package com.h3r3t1c.wearunitconverter.viewmodels

import android.content.Context
import android.os.Bundle
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.h3r3t1c.wearunitconverter.util.ConvertHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConvertActivityViewModel(context:Context, top:Int, bottom:Int, units:Array<Int>):ViewModel() {

    companion object {
        fun provideFactory(
            top: Int,
            bottom: Int,
            context: Context,
            units: Array<Int>,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return ConvertActivityViewModel(context, top, bottom, units) as T
                }
            }
    }


    private val _topUnit = MutableStateFlow(top)
    val topUnit = _topUnit.asStateFlow()

    private val _bottomUnit = MutableStateFlow(bottom)
    val bottomUnit = _bottomUnit.asStateFlow()

    private val _topText = MutableStateFlow("0")
    val topText = _topText.asStateFlow()

    private val _bottomText = MutableStateFlow(
        ConvertHelper.convertUnits(context,0.0, top, bottom)
    )
    val bottomText = _bottomText.asStateFlow()

    private val unitsList = units
    var activeUnitsList:Array<Int> = unitsList.copyOfRange(1, unitsList.size)

    fun updateTopUnit(context: Context, i:Int){
        _bottomText.value = ConvertHelper.convertUnits(context, topText.value.toDouble(), i,bottomUnit.value)
        _topUnit.value = i
        var index = 0
        for(item in unitsList){
            if(item == i) continue
            activeUnitsList[index] = item
            index++
        }
    }
    fun updateBottomUnit(context: Context, i:Int){
        _topText.value = ConvertHelper.convertUnits(context, bottomText.value.toDouble(), i,topUnit.value)
        _bottomUnit.value = i
    }

    fun updateTopText(context:Context, s:String){
        _topText.value = s
        _bottomText.value = ConvertHelper.convertUnits(context, s.toDouble(), topUnit.value, bottomUnit.value)
    }
    fun updateBottomText(context:Context, s:String){
        _bottomText.value = s
        _topText.value = ConvertHelper.convertUnits(context, s.toDouble(), bottomUnit.value, topUnit.value)
    }


}