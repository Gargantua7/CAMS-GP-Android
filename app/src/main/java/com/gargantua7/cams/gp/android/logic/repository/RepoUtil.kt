package com.gargantua7.cams.gp.android.logic.repository

import android.util.Log
import java.net.UnknownHostException

/**
 * @author Gargantua7
 */
suspend fun <T> fire(action: suspend () -> Result<T>): Result<T> {
    return try {
        action()
    } catch (e: UnknownHostException) {
        Result.failure(e)
    } catch (t: Throwable) {
        Log.d("Network Fire", t.message.toString())
        Result.failure(t)
    }
}
