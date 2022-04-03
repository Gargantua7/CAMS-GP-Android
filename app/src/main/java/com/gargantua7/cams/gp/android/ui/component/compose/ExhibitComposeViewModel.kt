package com.gargantua7.cams.gp.android.ui.component.compose

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.exception.NotFoundException
import kotlinx.coroutines.Dispatchers
import java.net.UnknownHostException

/**
 * @author Gargantua7
 */
abstract class ExhibitComposeViewModel<E : Any, ID : Any> : ComposeViewModel() {

    val id = MutableLiveData<ID?>(null)

    var fresh by mutableStateOf(false)

    var networkError by mutableStateOf(false)

    var item = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            if (it == null) emit(null)
            else emit(get(it))
        }
    }

    suspend fun get(id: ID): E? {
        loading = true
        val result = getItem(id)
        if (result.isSuccess) {
            loading = false
            return result.getOrThrow()
        } else {
            showSnackBar(
                when (result.exceptionOrNull()) {
                    is AuthorizedException -> "Invalid Session"
                    is NotFoundException -> "Not Found"
                    is UnknownHostException -> {
                        networkError = true
                        "Network Error"
                    }
                    else -> "Unknown Server Exception"
                }
            )
            Log.w("Get Repair($id) Has Exception", result.exceptionOrNull())
        }
        loading = false
        return null
    }

    abstract suspend fun getItem(id: ID): Result<E>

}
