package com.h3r3t1c.wearunitconverter.ui.compose.categories

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.h3r3t1c.wearunitconverter.util.CategoryHelper
import eu.hansolo.unit.converter.Converter

class ConvertOptionsViewModel(context: Context): ViewModel() {

    val options = mutableStateListOf<Option>()
    var selectedOption by mutableStateOf<Converter.Category?>(null)


    init {
        Converter.Category.entries.forEach {
            options.add(Option(CategoryHelper.getDisplayName(context, it), CategoryHelper.getIconForCategory(it), it))
        }
        options.sortBy { it.title }
    }
    companion object{
        fun getFactory(context: Context,): ViewModelProvider.Factory{
            val factory : ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    ConvertOptionsViewModel(context)
                }
            }
            return factory
        }
    }
}
class Option(val title: String, val iconResource: Int, val type: Converter.Category)
