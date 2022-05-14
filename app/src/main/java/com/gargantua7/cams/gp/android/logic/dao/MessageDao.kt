package com.gargantua7.cams.gp.android.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gargantua7.cams.gp.android.logic.model.LocalMsg

/**
 * @author Gargantua7
 */
@Dao
interface MessageDao {

    @Insert
    suspend fun insert(message: LocalMsg)

    @Query(
        """
        SELECT * 
        FROM msg 
        WHERE ((sender = :id AND recipient = :op)
        OR (recipient = :id AND sender = :op))
        AND master = :master
        ORDER BY time DESC
        LIMIT 10 offset :page * 10
        """
    )
    suspend fun queryById(id: String, op: String, page: Int, master: String): List<LocalMsg>

    @Query(
        """
        SELECT DISTINCT user
        FROM (
            SELECT sender AS user, time
            FROM msg
            WHERE recipient = :id AND master = :master
            UNION ALL
            SELECT recipient AS user, time
            FROM msg
            WHERE sender = :id AND master = :master
        )
        ORDER BY time DESC
        LIMIT 10 offset :page * 10
        """
    )
    suspend fun queryUsers(id: String, page: Int, master: String): List<String>

    @Query(
        """
        SELECT * 
        FROM msg 
        WHERE ((sender = :id AND recipient = :op)
        OR (recipient = :id AND sender = :op))
        AND master = :master
        ORDER BY time DESC
        LIMIT 1
        """
    )
    suspend fun queryLast(id: String, op: String, master: String): LocalMsg?

}
