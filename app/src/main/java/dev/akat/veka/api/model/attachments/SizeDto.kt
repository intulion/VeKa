package dev.akat.veka.api.model.attachments

import com.google.gson.annotations.SerializedName

class SizeDto(
    @SerializedName("height") val height: Int,
    @SerializedName("url") val url: String,
    @SerializedName("type") val type: String,
    @SerializedName("width") val width: Int,
)
