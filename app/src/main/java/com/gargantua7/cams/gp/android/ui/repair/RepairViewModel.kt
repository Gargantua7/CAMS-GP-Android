package com.gargantua7.cams.gp.android.ui.repair

import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.logic.paging.ReplyPagingSource
import com.gargantua7.cams.gp.android.logic.repository.RepairRepository
import com.gargantua7.cams.gp.android.logic.repository.ReplyRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ExhibitComposeViewModel
import com.gargantua7.cams.gp.android.ui.secret.SignInActivity
import com.gargantua7.cams.gp.android.ui.util.decodeImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Gargantua7
 */
class RepairViewModel : ExhibitComposeViewModel<Repair, Long>() {

    var editor by mutableStateOf("")

    val text = Transformations.switchMap(item) {
        liveData(Dispatchers.IO) {
            if (it == null) emit(null)
            else {
                val i = it.content.indexOf("<img>")
                emit(
                    if (i > 0)
                        it.content.substring(0, i)
                    else it.content
                )
            }
        }
    }

    val pics = Transformations.switchMap(item) {
        liveData(Dispatchers.IO) {
            if (it == null) emit(null)
            else {
                val list = ArrayList<Bitmap>()
                val res = "<img>([\\s\\S]*)<\\\\img>".toRegex().toPattern().matcher(it.content)
                if (res.find()) {
                    res.group(1)?.let { s ->
                        s.split(",").forEach { b ->
                            list.add(decodeImage(b))
                        }
                    }
                }
                emit(list)
            }
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

    override suspend fun getItem(id: Long) = RepairRepository.getRepairById(id)

    fun sendReply() {
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
                showSnackBar(
                    if (res.isSuccess) {
                        editor = ""
                        fresh = true
                        "Send Reply Success"
                    } else {
                        when (res.exceptionOrNull()) {
                            is AuthorizedException -> "Login Expired"
                            else -> "Unknown Exception"
                        }
                    }
                )
            }
        }
    }

    fun changeState() {
        viewModelScope.launch {
            val state = if (item.value?.state == true) RepairRepository.STATE_CLOSE else RepairRepository.STATE_OPEN
            id.value?.let { RepairRepository.changeState(it, state) }?.let {
                showSnackBar(
                    if (it.isSuccess) {
                        editor = ""
                        id.value = id.value
                        "State Change Success"
                    } else {
                        val e = it.exceptionOrNull()
                        when (e) {
                            is AuthorizedException -> "Insufficient Permissions"
                            else -> "Unknown Exception"
                        }
                    }
                )
            }
        }
    }

    fun assignPrinciple(principle: String) {
        if (principle == item.value?.principal?.username) {
            showSnackBar("Not Action Require")
            return
        }
        viewModelScope.launch {
            id.value?.let { RepairRepository.assignPrinciple(it, principle) }?.let {
                showSnackBar(
                    if (it.isSuccess) {
                        id.value = id.value
                        "Assign Principle Success"
                    } else {
                        val e = it.exceptionOrNull()
                        when (e) {
                            is AuthorizedException -> "Insufficient Permissions"
                            else -> "Unknown Exception"
                        }
                    }
                )
            }
        }
    }
}
