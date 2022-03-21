package com.gargantua7.cams.android.logic.repository

import com.gargantua7.cams.android.logic.network.EventService
import com.gargantua7.cams.android.logic.network.NetworkServiceCreator

/**
 * @author Gargantua7
 */
object EventRepository {

    private val eventService = NetworkServiceCreator.create(EventService::class.java)

    suspend fun getEvents(page: Int) = eventService.getEvents(page).get()

}
