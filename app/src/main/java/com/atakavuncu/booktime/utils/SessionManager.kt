package com.atakavuncu.booktime.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.atakavuncu.booktime.data.model.user.User
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
        const val EMAIL = "email"
        const val IS_LOGGED_IN = "is_logged_in"
    }

    fun saveUserSession(user: FirebaseUser) {
        val editor = preferences.edit()
        editor.putString(USER_ID, user.uid)
        editor.putString(EMAIL, user.email)
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.commit()
    }

    fun getUserId(): String? {
        return preferences.getString(USER_ID, null)
    }

    fun getEmail(): String? {
        return preferences.getString(EMAIL, null)
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun setIsLoggedIn(isLoggedIn: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
    }

    fun clearSession() {
        val editor = preferences.edit()
        editor.clear()
        editor.commit()
    }

    fun updateLanguage(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("App_Language", languageCode).apply()
    }

    fun loadLanguage(context: Context) {
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("App_Language", "tr")
        updateLanguage(context, languageCode ?: "tr")
    }

    fun getUserLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        return sharedPreferences.getString("App_Language", "tr") ?: "tr"
    }
}