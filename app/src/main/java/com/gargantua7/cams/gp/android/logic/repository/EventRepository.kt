package com.gargantua7.cams.gp.android.logic.repository

import com.gargantua7.cams.gp.android.logic.model.NewEvent
import com.gargantua7.cams.gp.android.logic.network.EventService
import com.gargantua7.cams.gp.android.logic.network.NetworkServiceCreator

/**
 * @author Gargantua7
 */
object EventRepository {

    private val eventService = NetworkServiceCreator.create(EventService::class.java)

    suspend fun getEventsList(page: Int) = fire { eventService.getEventsList(page).get() }

    suspend fun getEvent(id: Long) = fire { eventService.getEvent(id).get() }

    suspend fun signEvent(id: Long) = fire { eventService.signEvent(id).get() }

    suspend fun createNewEvent(event: NewEvent) = fire { eventService.createNewEvent(event).get() }

    suspend fun getSignPersonList(id: Long, page: Int) = fire { eventService.getSignPersonList(id, page).get() }

    suspend fun count(id: Long) = fire { eventService.count(id).get() }

    suspend fun sexGroup(id: Long) = fire { eventService.sexGroup(id).get() }

    suspend fun timeGroup(id: Long) = fire { eventService.timeGroup(id).get() }

    suspend fun collageGroup(id: Long) = fire { eventService.collageGroup(id).get() }

    suspend fun majorGroup(id: Long) = fire { eventService.majorGroup(id).get() }
}
