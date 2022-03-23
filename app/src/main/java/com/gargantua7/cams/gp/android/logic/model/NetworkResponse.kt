package com.gargantua7.cams.gp.android.logic.model

import com.gargantua7.cams.gp.android.logic.exception.ResultException

/**
 * @author Gargantua7
 */
data class NetworkResponse<T>(
    val code: Int,
    val msg: String,
    val info: String = "",
    val data: T? = null
) {

    fun get(): Result<T> {
        return when {
            code == 0 && data != null -> Result.success(data)
            else -> Result.failure(ResultException.build(code, msg, info))
        }
    }

}
