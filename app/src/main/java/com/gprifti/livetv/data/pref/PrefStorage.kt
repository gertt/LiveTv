package com.gprifti.livetv.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.gprifti.livetv.utils.Constants.Companion.KEY_EMAIL

class PrefStorage(context: Context?) {

    private val appContext = context?.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(
            appContext
        )

    suspend fun saveEmail(email: String) {
        preference.edit().putString(KEY_EMAIL, email).apply()
    }

    suspend fun getEmail(): String? {
        return preference.getString(KEY_EMAIL, null)
    }
}

