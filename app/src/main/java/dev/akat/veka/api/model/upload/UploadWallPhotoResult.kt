package dev.akat.veka.api.model.upload

import com.google.gson.annotations.SerializedName

class UploadWallPhotoResult(
    @SerializedName("id") val id: Int,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("album_id") val albumId: Int,
)

fun List<UploadWallPhotoResult>.getAttachmentIds(): String =
    this.joinToString(separator = ",") { "photo${it.ownerId}_${it.id}" }
