package dev.akat.veka.api.model.profile

import com.google.gson.annotations.SerializedName

class CityDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
)
