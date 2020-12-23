package dev.akat.veka.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "source_id") val sourceId: Int,
    @ColumnInfo(name = "source_name") val sourceName: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "likes") var likes: Int,
    @ColumnInfo(name = "comments") val comments: Int,
    @ColumnInfo(name = "shares") val shares: Int,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean,
)
