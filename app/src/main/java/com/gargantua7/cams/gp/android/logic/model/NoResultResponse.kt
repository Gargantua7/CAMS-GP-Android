package com.gargantua7.cams.gp.android.logic.model

import com.gargantua7.cams.gp.android.logic.exception.ResultException

/**
 * @author Gargantua7
 */
data class NoResultResponse(
    val code: Int,
    val msg: String,
    val info: String = ""
) {

    fun get(): Result<Unit> {
        return if (code == 0) Result.success(Unit)
        else Result.failure(ResultException.build(code, msg, info))
    }

}
