package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.*
import retrofit2.http.*

/**
 * @author Gargantua7
 */
interface EventService {

    @GET("event/list/{page}")
    suspend fun getEventsList(@Path("page") page: Int): NetworkResponse<List<Event>>

    @GET("event/{id}/get")
    suspend fun getEvent(@Path("id") id: Long): NetworkResponse<Event>

    @POST("event/{id}/sign")
    suspend fun signEvent(
        @Path("id") id: Long,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NoResultResponse

    @POST("event/create")
    suspend fun createNewEvent(
        @Body event: NewEvent,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NoResultResponse

    @GET("event/{id}/list/{page}")
    suspend fun getSignPersonList(
        @Path("id") id: Long,
        @Path("page") page: Int,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NetworkResponse<List<Person>>

    @GET("event/{id}/statistics/count")
    suspend fun count(@Path("id") id: Long): NetworkResponse<Int>

    @GET("event/{id}/statistics/groupBy/sex")
    suspend fun sexGroup(
        @Path("id") id: Long,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NetworkResponse<Map<String, Int>>

    @GET("event/{id}/statistics/groupBy/time")
    suspend fun timeGroup(
        @Path("id") id: Long,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NetworkResponse<Map<String, Int>>

    @GET("event/{id}/statistics/groupBy/collage")
    suspend fun collageGroup(
        @Path("id") id: Long,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NetworkResponse<Map<String, Int>>

    @GET("event/{id}/statistics/groupBy/major")
    suspend fun majorGroup(
        @Path("id") id: Long,
        @Header("session") session: String? = CAMSApplication.session.value
    ): NetworkResponse<Map<String, Int>>
}
