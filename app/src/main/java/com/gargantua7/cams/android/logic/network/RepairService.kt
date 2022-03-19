package com.gargantua7.cams.android.logic.network

import com.gargantua7.cams.android.logic.model.NetworkResponse
import com.gargantua7.cams.android.logic.model.Repair
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Gargantua7
 */
interface RepairService {

    @GET("repair/search/{page}")
    suspend fun searchRepair(
        @Path("page") page: Int,
        @Query(value = "id") id: Long? = null,
        @Query(value = "keyword") keyword: String? = null,
        @Query(value = "initiator") initiator: String? = null,
        @Query(value = "principal") principal: String? = null,
        @Query(value = "state") state: Boolean? = null,
        @Query(value = "unassigned") unassigned: Boolean = false
    ): NetworkResponse<List<Repair>>

}
