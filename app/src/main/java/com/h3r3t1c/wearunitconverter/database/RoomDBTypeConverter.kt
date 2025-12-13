package com.h3r3t1c.wearunitconverter.database

import android.icu.util.MeasureUnit
import androidx.room.TypeConverter
import com.h3r3t1c.wearunitconverter.util.ConverterType

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
    fun measureUnitToString(unit: MeasureUnit): String {
        return unit.toString()
    }

    @TypeConverter
    fun stringToMeasureUnit(value: String): MeasureUnit {
        ConverterType.entries.forEach {
            it.units.forEach { unit ->
                if(unit.toString() == value) return unit
            }
        }
        return MeasureUnit.ACRE
    }
}