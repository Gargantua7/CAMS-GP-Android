package com.gargantua7.cams.gp.android.logic.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gargantua7.cams.gp.android.logic.model.Message

/**
 * @author Gargantua7
 */
@Database(entities = [Message::class], version = 1)
abstract class MsgDatabase : RoomDatabase() {

    abstract fun getMsgDao(): MessageDao

}
