package com.example.devilcasinodemo.util

import android.content.Context

class TokenManager(context: Context) {

    private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun saveToken(token: String?) {
        if (token != null) {
            prefs.edit().putString("token", token).apply()
        }
    }



    fun saveUser(userId: Long, username: String?) {
        val editor = prefs.edit().putLong("userId", userId)
        if (username != null) {
            editor.putString("username", username)
        }
        editor.apply()
    }

    fun getUserId(): Long? {
        val id = prefs.getLong("userId", -1L)
        return if (id == -1L) null else id
    }

    fun getUsername(): String? {
        return prefs.getString("username", null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}