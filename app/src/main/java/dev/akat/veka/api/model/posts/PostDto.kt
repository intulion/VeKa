package dev.akat.veka.api.model.posts

import com.google.gson.annotations.SerializedName
import dev.akat.veka.api.model.attachments.AttachmentsDto

class PostDto(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: Long,
    @SerializedName("attachments") val attachments: List<AttachmentsDto>?,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("text") val text: String,
    @SerializedName("comments") val comments: CountDto?,
    @SerializedName("likes") val likes: LikesDto?,
    @SerializedName("reposts") val reposts: CountDto?,
)
