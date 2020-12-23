package dev.akat.veka.api.model

import com.google.gson.annotations.SerializedName

class ApiResponse<T>(
    @SerializedName("response") val response: T,
)
