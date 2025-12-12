package com.h3r3t1c.wearunitconverter.ui.compose.nav

import com.h3r3t1c.wearunitconverter.util.ConverterType

object NavDestination {
    const val HOME = "/home"
    const val CONVERT_PATH = "/convert_full/{type}/{inputNumber}/{from}/{to}"
    const val CONVERT_TO_OPTIONS_PATH = "/convert_to/{type}/{inputNumber}"

    fun getConvertToOptionsPath(type: ConverterType, number: String) = "/convert_to/${type.name}/${number}"
    fun getConvertPath(type: ConverterType, number: String, from: Int, to: Int) = "/convert_full/${type.name}/$number/$from/$to"

}
