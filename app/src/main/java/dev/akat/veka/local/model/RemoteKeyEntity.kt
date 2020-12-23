package dev.akat.veka.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "page_type") val pageType: Int,
    @ColumnInfo(name = "next_page_key") val nextPageKey: String,
)
