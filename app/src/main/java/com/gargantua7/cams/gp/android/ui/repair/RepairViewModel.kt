package com.gargantua7.cams.gp.android.ui.repair

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.exception.NotFoundException
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.logic.paging.ReplyPagingSource
import com.gargantua7.cams.gp.android.logic.repository.RepairRepository
import com.gargantua7.cams.gp.android.logic.repository.ReplyRepository
import com.gargantua7.cams.gp.android.ui.person.SignInActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

/**
 * @author Gargantua7
 */
class RepairViewModel : ViewModel() {

    var id = MutableLiveData<Long?>(null)

    var loading by mutableStateOf(false)

    var fresh by mutableStateOf(false)

    var errorMsg by mutableStateOf<String?>(null)

    var networkError by mutableStateOf(false)

    var stateChangeDialog by mutableStateOf(false)

    var editor by mutableStateOf("")

    val repair = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            if (it == null) emit(null)
            else emit(getRepair(it))
        }
    }

    val replies = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            if (it == null) emit(null)
            else emit(
                Pager(PagingConfig(pageSize = 10)) {
                    ReplyPagingSource(it)
                }.flow.cachedIn(viewModelScope)
            )
        }
    }

    suspend fun getRepair(id: Long): Repair? {
        loading = true
        val result = RepairRepository.getRepairById(id)
        if (result.isSuccess) {
            return result.getOrThrow()
        } else {
            errorMsg = when (result.exceptionOrNull()) {
                is AuthorizedException -> "Invalid Session"
                is NotFoundException -> "Not Found"
                is UnknownHostException -> {
                    networkError = true
                    "Network Error"
                }
                else -> "Unknown Server Exception"
            }
            Log.w("Get Repair($id) Has Exception", result.exceptionOrNull())
        }
        loading = false
        return null
    }

    fun sendRepair() {
        if (editor.isBlank()) return
        if (CAMSApplication.session.value == null) {
            CAMSApplication.context.apply {
                Intent(this, SignInActivity::class.java).let {
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(it)
                }
            }
            return
        }
        id.value?.let {
            viewModelScope.launch {
                val res = ReplyRepository.sendReplyForRepair(it, editor)
                errorMsg = if (res.isSuccess) {
                    editor = ""
                    fresh = true
                    "Send Reply Success"
                } else {
                    val e = res.exceptionOrNull()
                    when (e) {
                        is AuthorizedException -> "Login Expired"
                        else -> "Unknown Exception"
                    }
                }
            }
        }
    }

    fun changeState() {
        viewModelScope.launch {
            val state = if (repair.value?.state == true) RepairRepository.STATE_CLOSE else RepairRepository.STATE_OPEN
            val res = id.value?.let { RepairRepository.changeState(it, state) }
            res?.let {
                errorMsg = if (it.isSuccess) {
                    editor = ""
                    id.value = id.value
                    "State Change Success"
                } else {
                    val e = it.exceptionOrNull()
                    when (e) {
                        is AuthorizedException -> "Login Expired"
                        else -> "Unknown Exception"
                    }
                }
            }
        }
    }
}
