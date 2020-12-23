package dev.akat.veka.api.model.posts

import com.google.gson.annotations.SerializedName
import kotlin.math.absoluteValue

class GroupDto(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("screen_name") val screenName: String,
    @SerializedName("photo_50") val photo50: String,
    @SerializedName("photo_100") val photo100: String,
    @SerializedName("photo_200") val photo200: String,
)

fun List<GroupDto>.getName(id: Int): String? =
    this.find { it.id == id.absoluteValue }?.name

fun List<GroupDto>.getAvatarUrl(id: Int): String? =
    this.find { it.id == id.absoluteValue }?.photo100
