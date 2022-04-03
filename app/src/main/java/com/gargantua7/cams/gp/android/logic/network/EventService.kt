package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.logic.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Gargantua7
 */
interface EventService {

    @GET("event/list/{page}")
    suspend fun getEventsList(@Path("page") page: Int): NetworkResponse<List<Event>>

    @GET("event/{id}/get")
    suspend fun getEvent(@Path("id") id: Long): NetworkResponse<Event>
}
