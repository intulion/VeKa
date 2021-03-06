package dev.akat.veka.api.model.posts

import com.google.gson.annotations.SerializedName
import dev.akat.veka.api.model.attachments.AttachmentsDto

class NewsDto(
    @SerializedName("post_id") val id: Int,
    @SerializedName("date") val date: Long,
    @SerializedName("attachments") val attachments: List<AttachmentsDto>?,
    @SerializedName("source_id") val sourceId: Int,
    @SerializedName("text") val text: String,
    @SerializedName("comments") val comments: CountDto?,
    @SerializedName("likes") val likes: LikesDto?,
    @SerializedName("reposts") val reposts: CountDto?,
)
