package com.h3r3t1c.wearunitconverter.database

import androidx.room.TypeConverter
import com.h3r3t1c.wearunitconverter.util.ConverterType
import com.h3r3t1c.wearunitconverter.util.TypeUnit

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
    fun converterTypeToString(type: ConverterType): String {
        return type.name
    }

    @TypeConverter
    fun stringToConverterType(value: String): ConverterType {
        return try{ ConverterType.valueOf(value) }catch (_: Exception){
            ConverterType.TEMPERATURE
        }
    }

    @TypeConverter
    fun typeUnitToString(type: TypeUnit): String {
        return type.name
    }

    @TypeConverter
    fun stringToTypeUnit(value: String): TypeUnit {
        return try{ TypeUnit.valueOf(value)} catch (_: Exception){
            TypeUnit.CELSIUS
        }
    }

}