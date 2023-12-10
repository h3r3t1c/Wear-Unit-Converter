package com.h3r3t1c.wearunitconverter.util

import android.content.Context
import android.content.SharedPreferences

object AppPrefs {

    private const val PREF_INT_MAX_SIG_DIGITS = "max_sig_digits";

    fun getMaxSigDigits(context: Context):Int = getAppPrefs(context).getInt(PREF_INT_MAX_SIG_DIGITS, 4)
    fun setMaxSigDigits(context: Context, digits: Int) = getAppPrefs(context).edit().putInt(PREF_INT_MAX_SIG_DIGITS, digits).commit()
    fun getAppPrefs(context:Context):SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

}