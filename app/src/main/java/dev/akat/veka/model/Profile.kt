package dev.akat.veka.model

import java.util.Date

data class Profile(
    var id: Int,
    val domain: String,
    val name: String,
    val avatarUrl: String,
    val about: String,
    val city: String,
    val country: String,
    val lastSeen: Date,
    val birthday: String,
    val university: String,
    val company: String,
    var followers: Int,
    var friends: Int,
    var pages: Int,
) {
    fun getLocation(): String =
        listOf(city, country)
            .filter(String::isNotEmpty)
            .joinToString(separator = SEPARATOR)

    private companion object {
        const val SEPARATOR = ", "
    }
}
