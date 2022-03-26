package com.gargantua7.cams.gp.android.ui.repair

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.logic.model.RepairSearcher
import com.gargantua7.cams.gp.android.logic.repository.RepairRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.net.UnknownHostException

/**
 * @author Gargantua7
 */
class RepairViewModel : ViewModel() {

    var id = MutableLiveData<Long?>(null)

    var loading by mutableStateOf(false)

    var errorMsg by mutableStateOf<String?>(null)

    var networkError by mutableStateOf(false)

    val repair = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            if (it == null) emit(null)
            else emit(getRepair(it))
        }
    }

    suspend fun getRepair(id: Long): Repair? {
        loading = true
        val result = RepairRepository.searchRepair(
            0,
            RepairSearcher(id = id)
        )
        if (result.isSuccess) {
            val list = result.getOrThrow()
            if (list.size == 1) {
                return list.single()
            } else {
                Log.w("Get Repair($id) has Not Single", list.size.toString())
                errorMsg = "Not Found"
            }
        } else {
            CAMSApplication.errorMsg = when (result.exceptionOrNull()) {
                is AuthorizedException -> "Invalid Session"
                is UnknownHostException -> {
                    networkError = true
                    "Network Error"
                }
                else -> "Unknown Server Exception"
            }
            Log.w("Get Repair($id) Has Exception", result.exceptionOrNull())
        }
        loading = false
        delay(1000)
        errorMsg = null
        return null
    }
}
