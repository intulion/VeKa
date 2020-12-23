package dev.akat.veka.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.akat.veka.local.model.RemoteOffsetEntity

@Dao
interface RemoteOffsetDao {

    @Query("SELECT * FROM remote_offsets WHERE page_type = :pageType AND page_owner = :pageOwner")
    fun get(pageType: Int, pageOwner: Int): RemoteOffsetEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(remoteOffsets: RemoteOffsetEntity)
}
