package dev.akat.veka.api.model.comments

import com.google.gson.annotations.SerializedName
import dev.akat.veka.api.model.posts.GroupDto
import dev.akat.veka.api.model.posts.ProfileDto

class WallCommentsDto(
    @SerializedName("items") val items: List<CommentDto>,
    @SerializedName("groups") val groups: List<GroupDto>,
    @SerializedName("profiles") val profiles: List<ProfileDto>,
    @SerializedName("count") val count: Int,
    @SerializedName("current_level_count") val currentLevelCount: Int,
)
