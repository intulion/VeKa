package dev.akat.veka.api.model.comments

import com.google.gson.annotations.SerializedName
import dev.akat.veka.api.model.posts.CountDto

class CommentDto(
    @SerializedName("id") val id: Int,
    @SerializedName("from_id") val fromId: Int,
    @SerializedName("post_id") val postId: Int,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("date") val date: Long,
    @SerializedName("text") val text: String?,
    @SerializedName("likes") val likes: CountDto?,
)
