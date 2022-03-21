package com.gargantua7.cams.android.logic.model

import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
data class Event(
    val id: Long,
    val name: String,
    val content: String,
    val number: Int,
    val location: String,
    val eventTime: LocalDateTime,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
