package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.logic.model.Collage
import com.gargantua7.cams.gp.android.logic.model.Major
import com.gargantua7.cams.gp.android.logic.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Gargantua7
 */
interface ResourceService {

    @GET("res/list/collage")
    suspend fun getCollageList(): NetworkResponse<List<Collage>>

    @GET("res/get/collage/{id}")
    suspend fun getCollage(@Path("id") id: String): NetworkResponse<Collage>

    @GET("res/list/collage/{id}/major")
    suspend fun getCollageMajorList(@Path("id") id: String): NetworkResponse<List<Major>>

    @GET("res/get/major/{id}")
    suspend fun getMajor(@Path("id") id: String): NetworkResponse<Major>
}
