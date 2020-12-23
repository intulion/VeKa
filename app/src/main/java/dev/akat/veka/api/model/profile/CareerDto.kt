package dev.akat.veka.api.model.profile

import com.google.gson.annotations.SerializedName

class CareerDto(
    @SerializedName("city_id") val cityId: Int,
    @SerializedName("country_id") val countryId: Int,
    @SerializedName("from") val from: Int,
    @SerializedName("until") val until: Int?,
    @SerializedName("company") val company: String,
    @SerializedName("group_id") val groupId: Int?,
)
