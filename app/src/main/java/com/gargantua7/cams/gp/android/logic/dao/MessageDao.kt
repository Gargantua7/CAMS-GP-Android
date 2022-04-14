package com.gargantua7.cams.gp.android.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gargantua7.cams.gp.android.logic.model.Message

/**
 * @author Gargantua7
 */
@Dao
interface MessageDao {

    @Insert
    suspend fun insert(message: Message)

    @Query("SELECT * FROM msg WHERE sender = :id OR recipient = :id LIMIT 10 offset :page ORDER BY time ")
    suspend fun queryById(id: String, page: Int): List<Message>

    @Query(
        """
            SELECT sender user
            FROM msg
            WHERE sender = :id
            UNION
            SELECT recipient user
            FROM msg
            WHERE recipient = :id
            LIMIT 10 offset :page ORDER BY time
        """
    )
    suspend fun queryUsers(id: String, page: Int): List<String>

}
