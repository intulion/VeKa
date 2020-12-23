package dev.akat.veka.api.model.profile

import com.google.gson.annotations.SerializedName

class LastSeenDto(
    @SerializedName("platform") val platform: Int,
    @SerializedName("time") val time: Long,
)
