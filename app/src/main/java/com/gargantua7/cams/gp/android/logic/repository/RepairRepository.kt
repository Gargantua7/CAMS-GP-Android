package com.gargantua7.cams.gp.android.logic.repository

import com.gargantua7.cams.gp.android.logic.model.NewRepair
import com.gargantua7.cams.gp.android.logic.model.RepairSearcher
import com.gargantua7.cams.gp.android.logic.network.NetworkServiceCreator
import com.gargantua7.cams.gp.android.logic.network.RepairService

/**
 * @author Gargantua7
 */
object RepairRepository {

    private val repairService = NetworkServiceCreator.create<RepairService>()

    suspend fun getRepairById(id: Long) = fire { repairService.getRepairById(id).get() }

    suspend fun searchRepair(page: Int, searcher: RepairSearcher) = fire {
        repairService.searchRepair(
            page,
            searcher.id,
            searcher.keyword,
            searcher.initiator,
            searcher.principal,
            searcher.state,
            searcher.unassigned
        ).get()
    }

    const val STATE_OPEN = "open"
    const val STATE_CLOSE = "close"

    suspend fun changeState(id: Long, state: String) = fire {
        repairService.changeState(id, state).get()
    }

    suspend fun assignPrinciple(id: Long, principle: String) = fire {
        repairService.assignPrinciple(id, principle).get()
    }

    suspend fun createNewRepair(repair: NewRepair) = fire {
        repairService.createNewRepair(repair).get()
    }

}
