package com.gargantua7.cams.gp.android.ui.event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.compose.IconRow
import com.gargantua7.cams.gp.android.ui.component.compose.IconTextField
import com.gargantua7.cams.gp.android.ui.component.resizable.Resizable
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.component.topbar.SendTopBar
import com.gargantua7.cams.gp.android.ui.util.clearFocusOnKeyboardDismiss
import com.gargantua7.cams.gp.android.ui.util.format
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class NewEventActivity : ComposeActivity(), BackTopBar, SendTopBar, Resizable {

    private val title = "Create Event"
    override val viewModel by lazy { ViewModelProvider(this).get(NewEventViewModel::class.java) }


    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        if (viewModel.success) {
            setResult(RESULT_OK, Intent().putExtra("msg", "Event Created Successfully"))
            finish()
        }
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
                icon = Icons.Filled.Event,
                maxWords = 20
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
            Divider()
            IconRow(icon = Icons.Filled.Info, text = "Description")
            TextField(
                value = viewModel.content,
                onValueChange = { viewModel.content = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledTextColor = MaterialTheme.colors.onSurface,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clearFocusOnKeyboardDismiss()
            )

        }

    }

    override fun onSend() {
        viewModel.createNewEvent()
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
