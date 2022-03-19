package com.gargantua7.cams.android.logic.repository

import com.gargantua7.cams.android.logic.model.RepairSearcher
import com.gargantua7.cams.android.logic.network.NetworkServiceCreator
import com.gargantua7.cams.android.logic.network.RepairService

/**
 * @author Gargantua7
 */
object RepairRepository {

    private val repairService = NetworkServiceCreator.create<RepairService>()

    suspend fun searchRepair(page: Int, searcher: RepairSearcher) =
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
