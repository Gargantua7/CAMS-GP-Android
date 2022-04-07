package com.gargantua7.cams.gp.android.logic.model

import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
data class NewEvent(
    val name: String,
    val content: String,
    val number: Int,
    val location: String,
    val eventTime: LocalDateTime,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
