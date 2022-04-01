package com.gargantua7.cams.gp.android.logic.model

import java.io.Serializable

/**
 * @author Gargantua7
 */
data class PersonSearcher(
    val username: String? = null,
    val name: String? = null,
    val sex: Boolean? = null,
    val depId: Int? = null,
    val permissionLevel: Int? = null
) : Serializable, Searcher<Person>
