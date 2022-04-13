package com.gargantua7.cams.gp.android.logic.repository

import com.gargantua7.cams.gp.android.logic.network.NetworkServiceCreator
import com.gargantua7.cams.gp.android.logic.network.ResourceService

/**
 * @author Gargantua7
 */
object ResourceRepository {

    private val service = NetworkServiceCreator.create(ResourceService::class.java)

    suspend fun getCollageList() = fire { service.getCollageList().get() }

    suspend fun getCollageMajorList(id: String) = fire { service.getCollageMajorList(id).get() }

}
