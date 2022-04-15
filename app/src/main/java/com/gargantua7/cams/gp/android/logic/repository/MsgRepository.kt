package com.gargantua7.cams.gp.android.logic.repository

import android.util.Log
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.LocalMsg
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

    suspend fun saveMsgIntoDB(msg: LocalMsg) = CAMSApplication.username?.let {
        Log.d("MsgRepository", "Saving msg into DB")
        CAMSApplication.msgDB.getMsgDao().insert(msg).apply {
            CAMSApplication.msgRefresh = true
        }
    }

    suspend fun loadMsgFlowById(op: String, page: Int) = CAMSApplication.username?.let {
        Log.d("MsgRepository", "Loading msg flow by id")
        CAMSApplication.msgDB.getMsgDao().queryById(it, op, page, it)
    }

    suspend fun loadPersonListFromDB(page: Int) = CAMSApplication.username?.let {
        CAMSApplication.msgDB.getMsgDao().queryUsers(it, page, it)
    }


    suspend fun loadLastMsgFormDB(id: String) = CAMSApplication.username?.let {
        CAMSApplication.msgDB.getMsgDao().queryLast(it, id, it)
    }
}
