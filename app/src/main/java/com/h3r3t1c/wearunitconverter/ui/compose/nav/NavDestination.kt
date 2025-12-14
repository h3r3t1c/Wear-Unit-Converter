package com.h3r3t1c.wearunitconverter.ui.compose.nav

import eu.hansolo.unit.converter.Converter

object NavDestination {
    const val HOME = "/home"
    const val CONVERT_PATH = "/convert_full/{type}/{inputNumber}/{from}/{to}"
    const val CONVERT_TO_OPTIONS_PATH = "/convert_to/{type}/{inputNumber}"

    fun getConvertToOptionsPath(type: Converter.Category, number: String) = "/convert_to/${type.name}/${number}"
    fun getConvertPath(type: Converter.Category, number: String, from: Converter.UnitDefinition, to: Converter.UnitDefinition) = "/convert_full/${type.name}/$number/${from.name}/${to.name}"

}
