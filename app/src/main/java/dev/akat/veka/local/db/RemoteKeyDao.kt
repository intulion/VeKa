package dev.akat.veka.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.akat.veka.local.model.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Query("SELECT * FROM remote_keys WHERE page_type = :pageType")
    fun getByPageType(pageType: Int): RemoteKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(remoteKeys: RemoteKeyEntity)
}
