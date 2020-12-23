package dev.akat.veka.api.model.posts

import com.google.gson.annotations.SerializedName

class NewsFeedDto(
    @SerializedName("items") val items: List<NewsDto>,
    @SerializedName("groups") val groups: List<GroupDto>,
    @SerializedName("profiles") val profiles: List<ProfileDto>,
    @SerializedName("next_from") val nextFrom: String,
)
