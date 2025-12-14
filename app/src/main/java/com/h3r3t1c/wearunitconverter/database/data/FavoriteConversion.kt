package com.h3r3t1c.wearunitconverter.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import eu.hansolo.unit.converter.Converter

@Entity
data class FavoriteConversion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: Converter.Category,
    val from: Converter.UnitDefinition,
    val to: Converter.UnitDefinition,
) {
}