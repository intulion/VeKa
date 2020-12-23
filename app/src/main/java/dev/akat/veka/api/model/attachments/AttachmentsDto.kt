package dev.akat.veka.api.model.attachments

import com.google.gson.annotations.SerializedName

class AttachmentsDto(
    @SerializedName("type") val type: String,
    @SerializedName("photo") val photo: PhotoDto?,
)

fun List<AttachmentsDto>.getPhotoUrl(size: String): String? =
    this.firstOrNull()?.photo?.sizes?.find { it.type == size }?.url
