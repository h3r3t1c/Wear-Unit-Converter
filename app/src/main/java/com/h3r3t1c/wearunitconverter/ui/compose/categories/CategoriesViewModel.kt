package com.h3r3t1c.wearunitconverter.ui.compose.categories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.h3r3t1c.wearunitconverter.R
import com.h3r3t1c.wearunitconverter.util.ConverterType

class ConvertOptionsViewModel: ViewModel() {

    val options = mutableStateListOf<Option>()
    var selectedOption by mutableStateOf<ConverterType?>(null)

    init {
        options.add(Option(R.string.temperature, R.drawable.ic_temperature,  ConverterType.TEMPERATURE))
        options.add(Option(R.string.speed, R.drawable.ic_speed, ConverterType.SPEED))
        options.add(Option(R.string.length, R.drawable.ic_lenght, ConverterType.LENGTH))
        options.add(Option(R.string.weight, R.drawable.ic_weight, ConverterType.WEIGHT))
        options.add(Option(R.string.time, R.drawable.ic_time, ConverterType.TIME))
    }

}
class Option(val titleResource: Int, val iconResource: Int, val type: ConverterType)
