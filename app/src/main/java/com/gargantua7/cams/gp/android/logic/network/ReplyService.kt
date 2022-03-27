package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.NetworkResponse
import com.gargantua7.cams.gp.android.logic.model.NormalReply
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

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

}
