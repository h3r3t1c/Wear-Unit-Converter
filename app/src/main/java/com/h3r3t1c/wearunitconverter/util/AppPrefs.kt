package com.h3r3t1c.wearunitconverter.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object AppPrefs {

    private const val PREF_INT_MAX_SIG_DIGITS = "max_sig_digits"
    const val PREF_BOOL_USE_ENGINEER_NOTATION = "use_engineer_notation"

    fun getUseEngineerNotation(context: Context):Boolean = getAppPrefs(context).getBoolean(PREF_BOOL_USE_ENGINEER_NOTATION, false)
    fun setUseEngineerNotation(context: Context, use: Boolean) = getAppPrefs(context).edit(commit = true) { putBoolean(PREF_BOOL_USE_ENGINEER_NOTATION, use) }

    fun getMaxSigDigits(context: Context):Int = getAppPrefs(context).getInt(PREF_INT_MAX_SIG_DIGITS, 4)
    fun setMaxSigDigits(context: Context, digits: Int) = getAppPrefs(context).edit(commit = true) { putInt(PREF_INT_MAX_SIG_DIGITS, digits) }


    fun getAppPrefs(context:Context):SharedPreferences = context.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

}