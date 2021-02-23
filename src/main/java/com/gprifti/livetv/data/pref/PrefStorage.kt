package com.gprifti.livetv.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.gprifti.livetv.utils.KEY_EMAIL
import javax.inject.Inject

class PrefStorage @Inject constructor(context: Context) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences( appContext )

    fun saveEmail(email: String) {
        preference.edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(): String? {
        return preference.getString(KEY_EMAIL, "")
    }
}

