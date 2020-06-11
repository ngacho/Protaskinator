package com.brocodes.wedoit.mainactivity.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefs @Inject constructor(context: Context) {
    companion object {
        private const val PREFS_FILENAME = "com.brocodes.wedoit.prefs"
        private const val IS_DARK_MODE_ON = "is_dark_mode_on"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var isDarkMode: Boolean
        get() = prefs.getBoolean(IS_DARK_MODE_ON, true)
        set(value) = prefs.edit().putBoolean(IS_DARK_MODE_ON, value).apply()
}