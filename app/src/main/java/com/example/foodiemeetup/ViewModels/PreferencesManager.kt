package com.example.foodiemeetup.ViewModels

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(private val preferences: SharedPreferences) {

    companion object {
        fun create(context: Context): PreferencesManager {
            val preferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            return PreferencesManager(preferences)
        }
    }

    fun saveString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return preferences.getString(key, defaultValue) ?: defaultValue
    }
}