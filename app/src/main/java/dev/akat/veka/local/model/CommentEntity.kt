package dev.akat.veka.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "comments",
    indices = [Index("post_id")],
    foreignKeys = [ForeignKey(
        entity = PostEntity::class,
        parentColumns = ["id"],
        childColumns = ["post_id"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class CommentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "post_id") val postId: Int,
    @ColumnInfo(name = "owner_id") val ownerId: Int,
    @ColumnInfo(name = "source_id") val sourceId: Int,
    @ColumnInfo(name = "source_name") val sourceName: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "likes") var likes: Int,
)
