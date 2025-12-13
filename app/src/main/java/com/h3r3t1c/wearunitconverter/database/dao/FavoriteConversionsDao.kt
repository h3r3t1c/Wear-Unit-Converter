package com.h3r3t1c.wearunitconverter.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.h3r3t1c.wearunitconverter.database.data.FavoriteConversion

@Dao
interface FavoriteConversionsDao {

    @Query("SELECT * FROM FavoriteConversion")
    fun getAll(): PagingSource<Int, FavoriteConversion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(conversion: FavoriteConversion)

    @Delete
    fun remove(conversion: FavoriteConversion)
}