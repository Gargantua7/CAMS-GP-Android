package com.gargantua7.cams.gp.android.logic.repository

import com.gargantua7.cams.gp.android.logic.model.SendReply
import com.gargantua7.cams.gp.android.logic.network.NetworkServiceCreator
import com.gargantua7.cams.gp.android.logic.network.ReplyService

/**
 * @author Gargantua7
 */
object ReplyRepository {

    private val replyService = NetworkServiceCreator.create(ReplyService::class.java)

    suspend fun getReplyListByRepair(repairId: Long, page: Int) = fire {
        replyService.getReplyListByRepair(repairId, page).get()
    }

    suspend fun sendReplyForRepair(repairId: Long, content: String) = fire {
        replyService.sendReplyForRepair(repairId, SendReply(content)).get()
    }

    suspend fun getReplyById(replyId: Long) = fire {
        replyService.getReplyById(replyId).get()
    }

}
