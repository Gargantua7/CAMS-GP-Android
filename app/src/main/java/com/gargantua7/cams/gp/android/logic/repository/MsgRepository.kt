package com.gargantua7.cams.gp.android.logic.repository

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.Message
import com.gargantua7.cams.gp.android.logic.model.SendMsg
import com.gargantua7.cams.gp.android.logic.network.MsgService
import com.gargantua7.cams.gp.android.logic.network.NetworkServiceCreator

/**
 * @author Gargantua7
 */
object MsgRepository {

    private val service = NetworkServiceCreator.create(MsgService::class.java)

    suspend fun sendMsg(msg: SendMsg) = fire { service.sendMsg(msg).get() }

    suspend fun getMsg() = fire { service.getMsg().get() }

    suspend fun saveMsgIntoDB(msg: Message) = CAMSApplication.msgDB.value?.getMsgDao()?.insert(msg)

}
