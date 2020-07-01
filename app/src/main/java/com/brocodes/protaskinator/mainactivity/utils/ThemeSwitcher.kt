package com.brocodes.protaskinator.mainactivity.utils

import androidx.appcompat.app.AppCompatDelegate

fun switchTheme(sharedPrefs: SharedPrefs) = if (sharedPrefs.isDarkMode) {
    //switch to light mode
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    sharedPrefs.isDarkMode = false
} else {
    //switch to dark mode
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    sharedPrefs.isDarkMode = true
}

fun loadInitialTheme(sharedPrefs: SharedPrefs) = if (sharedPrefs.isDarkMode) {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
} else {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}