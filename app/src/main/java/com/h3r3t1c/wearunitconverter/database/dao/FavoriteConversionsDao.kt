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

    @Query("SELECT * FROM FavoriteConversion ORDER BY sortOrder ASC")
    fun getAll(): PagingSource<Int, FavoriteConversion>

    @Query("SELECT * FROM FavoriteConversion ORDER BY sortOrder ASC")
    fun getAllForSorting(): List<FavoriteConversion>

    @Query("SELECT * FROM FavoriteConversion LIMIT 4")
    fun getFavoritesForTile() : List<FavoriteConversion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(conversion: FavoriteConversion)

    @Delete
    fun remove(conversion: FavoriteConversion)
}