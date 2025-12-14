package com.h3r3t1c.wearunitconverter.database

import androidx.room.TypeConverter
import eu.hansolo.unit.converter.Converter

open class RoomDBTypeConverter {

    @TypeConverter
    fun listOfStringsToString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun stringToListOfStrings(value: String): List<String> {
        return value.split(",").map { it }
    }

   @TypeConverter
   fun categoryToString(category: Converter.Category): String {
       return category.name
   }

    @TypeConverter
    fun stringToCategory(value: String): Converter.Category {
        return Converter.Category.valueOf(value)
    }

    @TypeConverter
    fun unitDefinitionToString(unit: Converter.UnitDefinition): String {
        return unit.name
    }

    @TypeConverter
    fun stringToUnitDefinition(value: String): Converter.UnitDefinition {
        return Converter.UnitDefinition.valueOf(value)
    }

}