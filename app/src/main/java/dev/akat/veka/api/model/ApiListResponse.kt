package dev.akat.veka.api.model

import com.google.gson.annotations.SerializedName

class ApiListResponse<T>(
    @SerializedName("response") val response: List<T>,
)
