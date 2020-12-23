package dev.akat.veka.api.model.upload

import com.google.gson.annotations.SerializedName

class UploadWallPhotoServer(
    @SerializedName("album_id") val albumId: Int,
    @SerializedName("upload_url") val uploadUrl: String,
    @SerializedName("user_id") val userId: Int,
)
