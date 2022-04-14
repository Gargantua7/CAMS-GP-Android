package com.gargantua7.cams.gp.android.logic.model

import androidx.room.Entity
import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
@Entity(tableName = "msg")
data class Message(
    val sender: String,
    val recipient: String,
    val type: String,
    val content: String,
    val time: LocalDateTime
)
