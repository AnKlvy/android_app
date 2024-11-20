package com.example.myapplication.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    // Пример сохранения строки (например, пароля)
    fun savePassword(password: String) {
        sharedPreferences.edit().putString("password", password).apply()
    }

    // Пример получения строки
    fun getPassword(): String? {
        return sharedPreferences.getString("password", null)
    }

    // Пример сохранения boolean (например, для темной темы)
    fun saveDarkThemeEnabled(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean("dark_theme", isEnabled).apply()
    }

    fun isDarkThemeEnabled(): Boolean {
        return sharedPreferences.getBoolean("dark_theme", false)
    }

    // Пример сохранения количества объектов
    fun saveObjectCount(count: Int) {
        sharedPreferences.edit().putInt("object_count", count).apply()
    }

    fun getObjectCount(): Int {
        return sharedPreferences.getInt("object_count", 0)
    }
}
