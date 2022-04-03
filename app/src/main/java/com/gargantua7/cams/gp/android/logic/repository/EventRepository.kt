package com.gargantua7.cams.gp.android.logic.repository

import com.gargantua7.cams.gp.android.logic.network.EventService
import com.gargantua7.cams.gp.android.logic.network.NetworkServiceCreator

/**
 * @author Gargantua7
 */
object EventRepository {

    private val eventService = NetworkServiceCreator.create(EventService::class.java)

    suspend fun getEventsList(page: Int) = fire { eventService.getEventsList(page).get() }

    suspend fun getEvent(id: Long) = fire { eventService.getEvent(id).get() }
}
