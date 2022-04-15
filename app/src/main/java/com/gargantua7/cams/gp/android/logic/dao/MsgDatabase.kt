package com.gargantua7.cams.gp.android.logic.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.gargantua7.cams.gp.android.logic.model.LocalMsg
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Gargantua7
 */
@Database(entities = [LocalMsg::class], version = 1)
@TypeConverters(RoomLocalDateTimeConverter::class)
abstract class MsgDatabase : RoomDatabase() {

    abstract fun getMsgDao(): MessageDao

}

class RoomLocalDateTimeConverter {
    @TypeConverter
    fun roomToLocalDateTime(s: String): LocalDateTime =
        s.let { LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) }

    @TypeConverter
    fun roomToString(time: LocalDateTime): String = time.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
}
