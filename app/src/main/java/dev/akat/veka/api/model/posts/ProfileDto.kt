package dev.akat.veka.api.model.posts

import com.google.gson.annotations.SerializedName
import kotlin.math.absoluteValue

class ProfileDto(
    @SerializedName("id") val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("screen_name") val screenName: String,
    @SerializedName("photo_50") val photo50: String,
    @SerializedName("photo_100") val photo100: String,
) {
    fun getProfileName(): String =
        "$firstName $lastName".trim()
}

fun List<ProfileDto>.getName(id: Int): String? =
    this.find { it.id == id.absoluteValue }?.getProfileName()

fun List<ProfileDto>.getAvatarUrl(id: Int): String? =
    this.find { it.id == id.absoluteValue }?.photo100
