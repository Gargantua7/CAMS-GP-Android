package com.gargantua7.cams.gp.android.ui.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.exception.BadRequestException
import com.gargantua7.cams.gp.android.logic.model.NewEvent
import com.gargantua7.cams.gp.android.logic.repository.EventRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
class NewEventViewModel : ComposeViewModel() {

    var name by mutableStateOf("")

    var content by mutableStateOf("")

    var number by mutableStateOf("")

    var location by mutableStateOf("")

    var eventTime by mutableStateOf<LocalDateTime?>(null)

    var startTime by mutableStateOf<LocalDateTime?>(null)

    var endTime by mutableStateOf<LocalDateTime?>(null)

    var success by mutableStateOf(false)

    fun createNewEvent() {
        when {
            name.isBlank() -> showSnackBar("Event Name Should be Not Empty")
            name.length > 20 -> showSnackBar("Event Name Should be Less Than 20 Characters")
            location.isBlank() -> showSnackBar("Location Should be Not Empty")
            number.isBlank() -> showSnackBar("Number Should be Not Empty")
            !number.isDigitsOnly() -> showSnackBar("Number Should be Digits Only")
            number.toInt() < 0 -> showSnackBar("Number Should be Positive")
            startTime == null -> showSnackBar("Start Time Should be Not Empty")
            endTime == null -> showSnackBar("End Time Should be Not Empty")
            eventTime == null -> showSnackBar("Event Time Should be Not Empty")
            LocalDateTime.now() > startTime!! -> showSnackBar("Start Time Should be Greater Than Current Time")
            startTime!! > endTime!! -> showSnackBar("End Time Should be Greater Than Start Time")
            endTime!! > eventTime!! -> showSnackBar("Event Time Should be Greater Than End Time")
            else -> viewModelScope.launch {
                val res = EventRepository.createNewEvent(
                    NewEvent(name, content, number.toInt(), location, eventTime!!, startTime!!, endTime!!)
                )
                if (res.isSuccess) {
                    success = true
                } else {
                    showSnackBar(
                        when (val e = res.exceptionOrNull()) {
                            is BadRequestException -> "Parameter is invalid: ${e.info}"
                            is AuthorizedException -> "You are not authorized to create event"
                            else -> "Unknown Exception"
                        }
                    )
                }
            }
        }
    }
}
