package dev.akat.veka.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "remote_offsets",
    primaryKeys = ["page_type", "page_owner"]
)
data class RemoteOffsetEntity(
    @ColumnInfo(name = "page_type") val pageType: Int,
    @ColumnInfo(name = "page_owner") val pageOwner: Int,
    @ColumnInfo(name = "next_offset") val nextOffset: Int,
)
