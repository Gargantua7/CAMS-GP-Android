package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.NetworkResponse
import com.gargantua7.cams.gp.android.logic.model.Person
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Gargantua7
 */
interface PersonService {

    @GET("person/info/search/{page}")
    suspend fun searchPerson(
        @Path("page") page: Int = 0,
        @Query("username") username: String? = null,
        @Query("name") name: String? = null,
        @Query("sex") sex: Boolean? = null,
        @Query("depId") depId: Int? = null,
        @Query("permissionLevel") permissionLevel: Int? = null,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NetworkResponse<List<Person>>

}
