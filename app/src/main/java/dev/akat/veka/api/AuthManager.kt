package dev.akat.veka.api

import dev.akat.veka.data.AppPreferences
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthManager @Inject constructor(private val preferences: AppPreferences) {

    class Credentials(
        val token: String,
        val expirationDate: Long,
    )

    var credentials: Credentials
        get() = Credentials(
            preferences.token,
            preferences.expirationDate,
        )
        set(value) {
            preferences.token = value.token
            preferences.expirationDate =
                value.expirationDate + TimeUnit.HOURS.toMillis(EXPIRES_IN_HOURS)
        }

    fun isLoggedIn(): Boolean =
        credentials.token.isNotEmpty() && credentials.expirationDate > System.currentTimeMillis()

    private companion object {
        const val EXPIRES_IN_HOURS = 24L
    }
}
