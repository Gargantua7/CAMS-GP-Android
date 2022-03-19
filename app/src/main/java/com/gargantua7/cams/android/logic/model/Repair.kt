package com.gargantua7.cams.android.logic.model

import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
data class Repair(
    val id: Long,
    val title: String,
    val content: String,
    val initiator: Person,
    val principal: Person?,
    val initTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val state: Boolean = true,
    val private: Boolean = false,
    val reply: Int
)
