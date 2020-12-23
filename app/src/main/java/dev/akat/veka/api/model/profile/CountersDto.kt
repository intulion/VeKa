package dev.akat.veka.api.model.profile

import com.google.gson.annotations.SerializedName

class CountersDto(
    @SerializedName("followers") val followers: Int,
    @SerializedName("friends") val friends: Int,
    @SerializedName("pages") val pages: Int,
)
