package com.gargantua7.cams.gp.android.logic.model

import java.io.Serializable

/**
 * @author Gargantua7
 */
data class RepairSearcher(
    val id: Long? = null,
    val keyword: String? = null,
    val initiator: String? = null,
    val principal: String? = null,
    val state: Boolean? = null,
    val unassigned: Boolean = false
) : Serializable, Searcher<Repair>
