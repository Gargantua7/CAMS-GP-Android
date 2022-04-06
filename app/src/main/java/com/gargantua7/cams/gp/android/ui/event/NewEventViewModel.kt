package com.gargantua7.cams.gp.android.ui.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
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
}
