package com.gargantua7.cams.gp.android.ui.event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.compose.IconTextField
import com.gargantua7.cams.gp.android.ui.component.resizable.Resizable
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.util.format
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class NewEventActivity : ComposeActivity(), BackTopBar, Resizable {

    private val title = "Create Event"
    override val viewModel by lazy { ViewModelProvider(this).get(NewEventViewModel::class.java) }


    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface)
                .verticalScroll(rememberScrollState())
        ) {
            IconTextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = "Title",
                icon = Icons.Filled.Event
            )
            IconTextField(
                value = viewModel.location,
                onValueChange = { viewModel.location = it },
                label = "Location",
                icon = Icons.Filled.Place
            )
            IconTextField(
                value = viewModel.number,
                onValueChange = { viewModel.number = it },
                label = "Number",
                icon = Icons.Filled.Groups,
            )
            IconTextField(
                value = (viewModel.startTime?.format() ?: ""),
                icon = Icons.Filled.Alarm,
                label = "Registration Start Date Time",
                onClick = {
                    var choose: LocalDate
                    datePicker(viewModel.startTime) { date ->
                        choose = date
                        timePicker(viewModel.startTime) { time ->
                            viewModel.startTime =
                                LocalDateTime.of(choose, time)
                        }.show()
                    }.show()
                }
            )
            IconTextField(
                value = (viewModel.endTime?.format() ?: ""),
                label = "Registration End Date Time",
                icon = Icons.Filled.AlarmOff,
                onClick = {
                    var choose: LocalDate
                    datePicker(viewModel.endTime) { date ->
                        choose = date
                        timePicker(viewModel.endTime) { time ->
                            viewModel.endTime =
                                LocalDateTime.of(choose, time)
                        }.show()
                    }.show()
                }
            )
            IconTextField(
                value = (viewModel.eventTime?.format() ?: ""),
                label = "Event Date Time",
                icon = Icons.Filled.DateRange,
                onClick = {
                    var choose: LocalDate
                    datePicker(viewModel.eventTime) { date ->
                        choose = date
                        timePicker(viewModel.eventTime) { time ->
                            viewModel.eventTime =
                                LocalDateTime.of(choose, time)
                        }.show()
                    }.show()
                }
            )
        }

    }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = title,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

    private fun datePicker(
        default: LocalDateTime? = null,
        datePicker: (LocalDate) -> Unit
    ): DatePickerDialog {
        val defaultDate = default?.toLocalDate() ?: LocalDate.now()
        return DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                datePicker(LocalDate.of(year, month + 1, dayOfMonth))
            }, defaultDate.year, defaultDate.monthValue - 1, defaultDate.dayOfMonth
        )
    }

    private fun timePicker(
        default: LocalDateTime? = null,
        timePicker: (LocalTime) -> Unit
    ): TimePickerDialog {
        val defaultTime = default?.toLocalTime() ?: LocalTime.now()
        return TimePickerDialog(
            this,
            { _, hour, minute ->
                timePicker(LocalTime.of(hour, minute, 0))
            }, defaultTime.hour, defaultTime.minute, true
        )
    }
}
