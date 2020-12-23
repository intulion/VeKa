package dev.akat.veka.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "news",
    foreignKeys = [ForeignKey(
        entity = PostEntity::class,
        parentColumns = ["id"],
        childColumns = ["post_id"],
        onDelete = CASCADE
    )]
)
data class NewsEntity(
    @PrimaryKey
    @ColumnInfo(name = "post_id") val postId: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
)
