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

    override fun hashCode(): Int {
        return id + type.hashCode() + from.hashCode() + to.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as FavoriteConversion
        if (id != other.id) return false
        if (type != other.type) return false
        if (from != other.from) return false
        if (to != other.to) return false
        return true
    }
}