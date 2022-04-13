package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.NetworkResponse
import com.gargantua7.cams.gp.android.logic.model.NoResultResponse
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.logic.model.PersonUpdate
import retrofit2.http.*

/**
 * @author Gargantua7
 */
interface PersonService {

    @GET("person/info/get/me")
    suspend fun getMe(@Header("session") session: String? = CAMSApplication.session.value): NetworkResponse<Person>

    @GET("person/info/get/{username}")
    suspend fun getPersonByUsername(
        @Path("username") username: String,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NetworkResponse<Person>

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

    @POST("person/info/update")
    suspend fun updatePerson(
        @Body info: PersonUpdate,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NoResultResponse
}
