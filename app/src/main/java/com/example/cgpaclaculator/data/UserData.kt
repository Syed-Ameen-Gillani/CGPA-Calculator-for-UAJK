package com.example.cgpaclaculator.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class UserProfile(
    val name: String = "",
    val department: String = "Computer Science"
)

data class HistoryItem(
    val title: String,
    val result: String,
    val date: String,
    val type: String // GPA, CGPA, etc.
)

class UserDataManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveProfile(profile: UserProfile) {
        prefs.edit().putString("name", profile.name).putString("dept", profile.department).apply()
    }

    fun getProfile(): UserProfile {
        val name = prefs.getString("name", "") ?: ""
        val dept = prefs.getString("dept", "Computer Science") ?: "Computer Science"
        return UserProfile(name, dept)
    }

    fun saveHistory(history: List<HistoryItem>) {
        val json = gson.toJson(history)
        prefs.edit().putString("history", json).apply()
    }

    fun getHistory(): List<HistoryItem> {
        val json = prefs.getString("history", null) ?: return emptyList()
        val type = object : TypeToken<List<HistoryItem>>() {}.type
        return gson.fromJson(json, type)
    }
}
