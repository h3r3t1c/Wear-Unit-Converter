package com.h3r3t1c.wearunitconverter.util

import android.content.Context
import android.content.SharedPreferences

object AppPrefs {

    const val PREF_INT_MAX_SIG_DIGITS = "max_sig_digits";

    fun getMaxSigDigits(context: Context):Int{
        return getAppPrefs(context).getInt(PREF_INT_MAX_SIG_DIGITS, 4)
    }

    fun getAppPrefs(context:Context):SharedPreferences{
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
}