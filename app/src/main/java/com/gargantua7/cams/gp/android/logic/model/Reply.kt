package com.gargantua7.cams.gp.android.logic.model

import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
interface Reply {
    val id: Long
    val repairId: Long
    val repairTitle: String
    val sender: Person
    val time: LocalDateTime
}

data class NormalReply(
    override val id: Long,
    override val repairId: Long,
    override val repairTitle: String,
    override val sender: Person,
    val type: Int,
    val content: String,
    override val time: LocalDateTime
) : Reply

data class PersonReply(
    override val id: Long,
    override val repairId: Long,
    override val repairTitle: String,
    override val sender: Person,
    val content: Person,
    override val time: LocalDateTime
) : Reply
