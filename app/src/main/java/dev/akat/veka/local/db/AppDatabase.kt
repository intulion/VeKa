package dev.akat.veka.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.akat.veka.local.model.CommentEntity
import dev.akat.veka.local.model.NewsEntity
import dev.akat.veka.local.model.PostEntity
import dev.akat.veka.local.model.ProfileEntity
import dev.akat.veka.local.model.RemoteKeyEntity
import dev.akat.veka.local.model.RemoteOffsetEntity

@Database(
    entities = [
        PostEntity::class,
        NewsEntity::class,
        ProfileEntity::class,
        CommentEntity::class,
        RemoteKeyEntity::class,
        RemoteOffsetEntity::class,
    ],
    version = 6
)
@TypeConverters(PostTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    abstract fun newsDao(): NewsDao

    abstract fun profileDao(): ProfileDao

    abstract fun commentDao(): CommentDao

    abstract fun remoteKeyDao(): RemoteKeyDao

    abstract fun remoteOffsetDao(): RemoteOffsetDao
}
