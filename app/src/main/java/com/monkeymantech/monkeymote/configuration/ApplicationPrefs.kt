package com.monkeymantech.monkeymote.configuration

import android.content.Context
import android.content.SharedPreferences

class ApplicationPrefs(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
}