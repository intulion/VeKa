package dev.akat.veka.api.model.upload

import com.google.gson.annotations.SerializedName

class UploadWallPhotoFile(
    @SerializedName("hash") val hash: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("server") val server: Int
)
