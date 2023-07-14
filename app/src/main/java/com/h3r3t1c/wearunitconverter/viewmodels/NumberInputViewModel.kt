package com.h3r3t1c.wearunitconverter.viewmodels

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NumberInputViewModel(init:String): ViewModel() {

    companion object {
        fun provideFactory(
            init: String,
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
                    return NumberInputViewModel(init) as T
                }
            }
    }

    private val _value = MutableStateFlow(init)
    val value = _value.asStateFlow()

    fun updateValue(key:String){
        var input= value.value
        _value.value =  when (key) {
            "CE" -> "0"
            "back" ->{
                if (input.isNotEmpty()){
                    input = input.dropLast(1)
                    if(input.equals("-") || input.isEmpty() || input.equals("-0"))
                        "0"
                    else input
                }
                else "0"
            }
            "." -> if (input.isEmpty()) "0." else if (input.contains(".")) input else input + key
            "-" -> if(input.startsWith("-")) input.drop(1) else key+input
            else ->{
                if(input.startsWith("0.") || input.startsWith("-0.")) input + key
                else if(input.equals("0"))
                    key
                else input+key
            }
        }

    }

}