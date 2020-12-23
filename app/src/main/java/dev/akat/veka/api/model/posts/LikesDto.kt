package dev.akat.veka.api.model.posts

import com.google.gson.annotations.SerializedName

class LikesDto(
    @SerializedName("count") val count: Int,
    @SerializedName("user_likes") val userLikes: Int,
    @SerializedName("can_like") val canLike: Int,
    @SerializedName("can_publish") val canPublish: Int,
)
