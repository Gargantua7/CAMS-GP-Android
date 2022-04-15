package com.gargantua7.cams.gp.android.logic.model

import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
data class Message(
    val sender: String,
    val recipient: String,
    val type: String,
    val content: String,
    val time: LocalDateTime
)
