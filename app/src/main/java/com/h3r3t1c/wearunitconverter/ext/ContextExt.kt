package com.h3r3t1c.wearunitconverter.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.h3r3t1c.wearunitconverter.database.AppDatabase
import com.h3r3t1c.wearunitconverter.database.dao.FavoriteConversionsDao

val Context.favsDatabase: FavoriteConversionsDao
    get() = AppDatabase.getInstance(this).favoriteConversionsDao()


fun Context.findActivity(): Activity {

    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}