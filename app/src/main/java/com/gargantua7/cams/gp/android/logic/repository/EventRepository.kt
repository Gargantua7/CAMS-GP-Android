package com.gargantua7.cams.gp.android.logic.repository

import com.gargantua7.cams.gp.android.logic.network.EventService
import com.gargantua7.cams.gp.android.logic.network.NetworkServiceCreator

/**
 * @author Gargantua7
 */
object EventRepository {

    private val eventService = NetworkServiceCreator.create(EventService::class.java)

    suspend fun getEvents(page: Int) = fire { eventService.getEvents(page).get() }

}
