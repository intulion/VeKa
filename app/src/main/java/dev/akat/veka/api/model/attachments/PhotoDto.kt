package dev.akat.veka.api.model.attachments

import com.google.gson.annotations.SerializedName

class PhotoDto(
    @SerializedName("sizes") val sizes: List<SizeDto>?,
)
