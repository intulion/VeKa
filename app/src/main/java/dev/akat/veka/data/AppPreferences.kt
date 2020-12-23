package dev.akat.veka.data

import android.content.Context
import androidx.preference.PreferenceManager
import javax.inject.Inject

class AppPreferences @Inject constructor(private val context: Context) {

    var token: String
        get() = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(KEY_TOKEN, "").orEmpty()
        set(value) = PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(KEY_TOKEN, value)
            .apply()

    var expirationDate: Long
        get() = PreferenceManager.getDefaultSharedPreferences(context)
            .getLong(KEY_EXPIRATION, 0L)
        set(value) = PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putLong(KEY_EXPIRATION, value)
            .apply()

    private companion object {
        const val KEY_TOKEN = "token"
        const val KEY_EXPIRATION = "expiration_date"
    }
}
