package com.h3r3t1c.wearunitconverter.database.data

import android.icu.util.MeasureUnit
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.h3r3t1c.wearunitconverter.util.ConverterType

@Entity
data class FavoriteConversion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: ConverterType,
    val from: MeasureUnit,
    val to: MeasureUnit,
) {
}