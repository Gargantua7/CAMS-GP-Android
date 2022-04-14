package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.Message
import com.gargantua7.cams.gp.android.logic.model.NetworkResponse
import com.gargantua7.cams.gp.android.logic.model.NoResultResponse
import com.gargantua7.cams.gp.android.logic.model.SendMsg
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @author Gargantua7
 */
interface MsgService {

    @POST("msg/send")
    suspend fun sendMsg(
        @Body msg: SendMsg,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NoResultResponse

    @POST("msg/get")
    suspend fun getMsg(
        @Header("session") session: String? = CAMSApplication.session.value
    ): NetworkResponse<List<Message>>

}
