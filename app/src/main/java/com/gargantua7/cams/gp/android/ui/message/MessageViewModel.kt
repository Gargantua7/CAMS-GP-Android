package com.gargantua7.cams.gp.android.ui.message

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.LocalMsg
import com.gargantua7.cams.gp.android.logic.model.SendMsg
import com.gargantua7.cams.gp.android.logic.paging.MsgFlowPagingSource
import com.gargantua7.cams.gp.android.logic.repository.MsgRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
class MessageViewModel : ComposeViewModel() {

    var op = MutableLiveData<String?>()

    var title by mutableStateOf("Message")

    var editor by mutableStateOf("")

    var msg = Transformations.switchMap(op) {
        liveData(Dispatchers.IO) {
            if (it == null) emit(null)
            else emit(
                Pager(PagingConfig(pageSize = 10)) {
                    MsgFlowPagingSource(it)
                }.flow.cachedIn(viewModelScope)
            )
        }
    }

    fun send() {
        op.value?.let {
            val msg = SendMsg(it, editor)
            if (CAMSApplication.username == null) return
            viewModelScope.launch {
                val res = MsgRepository.sendMsg(msg)
                try {
                    res.getOrThrow()
                    val localMsg = LocalMsg(
                        0,
                        CAMSApplication.username ?: "",
                        CAMSApplication.username!!,
                        it,
                        title,
                        LocalMsg.Type.NORMAL,
                        editor,
                        LocalDateTime.now()
                    )
                    MsgRepository.saveMsgIntoDB(localMsg)
                    editor = ""
                } catch (_: Exception) {
                    showSnackBar("Network Error")
                }
            }
        }
    }

}
