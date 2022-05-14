package com.gargantua7.cams.gp.android.ui.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.gargantua7.cams.gp.android.logic.exception.BadRequestException
import com.gargantua7.cams.gp.android.logic.exception.ForbiddenException
import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.logic.repository.EventRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ExhibitComposeViewModel
import kotlinx.coroutines.launch

/**
 * @author Gargantua7
 */
class EventViewModel : ExhibitComposeViewModel<Event, Long>() {

    var success by mutableStateOf(false)

    override suspend fun getItem(id: Long) = EventRepository.getEvent(id)

    fun signEvent() {
        id.value?.let {
            viewModelScope.launch {
                val res = EventRepository.signEvent(it)
                if (res.isSuccess) {
                    showSnackBar("报名成功")
                    success = true
                } else {
                    showSnackBar(
                        when (val e = res.exceptionOrNull()!!) {
                            is BadRequestException -> "Currently Not Allowed"
                            is ForbiddenException ->
                                if ("full" in e.info) "人数已满"
                                else {
                                    success = true
                                    "请勿重复报名"
                                }
                            else -> "Unknown Exception"
                        }
                    )
                }
            }
        }
    }
}
