package com.h3r3t1c.wearunitconverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.h3r3t1c.wearunitconverter.database.dao.FavoriteConversionsDao
import com.h3r3t1c.wearunitconverter.database.data.FavoriteConversion

@Database(entities = [FavoriteConversion::class], version = 1)
@TypeConverters(RoomDBTypeConverter::class,)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteConversionsDao(): FavoriteConversionsDao

    companion object {
        private const val DATABASE_NAME = "app_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .enableMultiInstanceInvalidation()
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}