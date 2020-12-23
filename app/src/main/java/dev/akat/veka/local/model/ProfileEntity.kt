package dev.akat.veka.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "domain") val domain: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "about") val about: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "lastSeen") val lastSeen: Date,
    @ColumnInfo(name = "birthday") val birthday: String,
    @ColumnInfo(name = "university") val university: String,
    @ColumnInfo(name = "company") var company: String,
    @ColumnInfo(name = "followers") val followers: Int,
    @ColumnInfo(name = "friends") val friends: Int,
    @ColumnInfo(name = "pages") val pages: Int,
)
