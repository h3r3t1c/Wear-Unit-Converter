package com.h3r3t1c.wearunitconverter.ui.compose.nav

import eu.hansolo.unit.converter.Converter

object NavDestination {
    const val HOME = "/home"
    const val CONVERT_PATH = "/convert_full/{type}/{inputNumber}/{from}/{to}"
    const val CONVERT_TO_OPTIONS_PATH = "/convert_to/{type}/{inputNumber}"
    const val QUICK_CONVERT_PATH = "/quick_convert"
    const val REORDER_FAVORITES = "/reorder_favorites"



    fun getConvertToOptionsPath(type: Converter.Category, number: String) = "/convert_to/${type.name}/${number}"
    fun getConvertPath(type: Converter.Category, number: String, from: Converter.UnitDefinition, to: Converter.UnitDefinition) = getConvertPath(type.name, number, from.name, to.name)
    fun getConvertPath(type: String, number: String, from: String, to: String) = "/convert_full/$type/$number/$from/$to"

}
