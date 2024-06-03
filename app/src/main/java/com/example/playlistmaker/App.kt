package com.example.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val DARK_THEME = "dark_theme"
var DARK_THEME_VALUE: Boolean = false

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences(DARK_THEME, MODE_PRIVATE)
        DARK_THEME_VALUE = sharedPreferences.getBoolean(DARK_THEME, false)
        switchTheme(DARK_THEME_VALUE)
    }


    fun switchTheme(darkThemeEnabled: Boolean) {

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    fun saveShared(darkThemeEnabled: Boolean) {
        val sharedPreferences = getSharedPreferences(DARK_THEME, MODE_PRIVATE)
        sharedPreferences.edit()
            .putBoolean(DARK_THEME, darkThemeEnabled)
            .apply()
    }


}

