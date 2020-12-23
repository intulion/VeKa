package dev.akat.veka.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.akat.veka.local.model.ProfileEntity
import io.reactivex.Observable

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profiles ORDER BY profiles.lastSeen DESC LIMIT 1")
    fun getProfile(): Observable<ProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: ProfileEntity)
}
