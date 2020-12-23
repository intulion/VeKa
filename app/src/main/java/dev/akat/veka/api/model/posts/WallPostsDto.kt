package dev.akat.veka.api.model.posts

import com.google.gson.annotations.SerializedName

class WallPostsDto(
    @SerializedName("items") val items: List<PostDto>,
    @SerializedName("groups") val groups: List<GroupDto>,
    @SerializedName("profiles") val profiles: List<ProfileDto>,
    @SerializedName("count") val count: Int,
)
