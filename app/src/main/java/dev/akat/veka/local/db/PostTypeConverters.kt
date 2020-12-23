package dev.akat.veka.local.db

import androidx.room.TypeConverter
import java.util.Date

class PostTypeConverters {

    @TypeConverter
    fun dateToDatestamp(date: Date): Long = date.time

    @TypeConverter
    fun datestampToDate(value: Long): Date = Date(value)
}
