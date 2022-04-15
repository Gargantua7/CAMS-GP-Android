package com.gargantua7.cams.gp.android.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gargantua7.cams.gp.android.CAMSApplication
import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
@Entity(tableName = "msg")
data class LocalMsg(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val master: String,
    val sender: String,
    val recipient: String,
    val opName: String,
    val type: Type,
    val content: String,
    val time: LocalDateTime,
    val target: Long = -1
) {

    enum class Type {
        NORMAL,
        REPAIR,
        REPLY,
        EVENT;

        companion object {
            fun fromString(s: String): Type {
                return when (s) {
                    "Normal" -> NORMAL
                    "Repair" -> REPAIR
                    "Reply" -> REPLY
                    "Event" -> EVENT
                    else -> throw IllegalArgumentException("$s is not a valid type")
                }
            }
        }
    }


    constructor(msg: Message, name: String) : this(
        0,
        CAMSApplication.username ?: "",
        msg.sender,
        msg.recipient,
        name,
        Type.fromString(msg.type),
        when (msg.type) {
            "Repair" -> if (msg.recipient == CAMSApplication.username) {
                "你已被指定为求助帖的负责人，请及时处理"
            } else {
                "你的求助贴已被管理员指定了负责人，请查看"
            }
            "Reply" -> "与你有关的求助贴收到了新的回复，请查看"
            "Event" -> "活动报名成功，请查看"
            else -> msg.content
        },
        msg.time,
        if (msg.type != "Normal") msg.content.toLong() else -1
    )

}
