package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.NetworkResponse
import com.gargantua7.cams.gp.android.logic.model.NoResultResponse
import com.gargantua7.cams.gp.android.logic.model.NormalReply
import com.gargantua7.cams.gp.android.logic.model.SendReply
import retrofit2.http.*

/**
 * @author Gargantua7
 */
interface ReplyService {

    @GET("repair/{repairId}/reply/list/{page}")
    suspend fun getReplyListByRepair(
        @Path("repairId") repair: Long,
        @Path("page") page: Int,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NetworkResponse<List<NormalReply>>

    @POST("repair/{repairId}/reply/add")
    suspend fun sendReplyForRepair(
        @Path("repairId") repair: Long,
        @Body reply: SendReply,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NoResultResponse

    @GET("repair/reply/get/{id}")
    suspend fun getReplyById(
        @Path("id") id: Long,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NetworkResponse<NormalReply>

    @POST("repair/reply/delete/{id}")
    suspend fun deleteReplyById(
        @Path("id") id: Long,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NoResultResponse

}
