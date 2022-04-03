package com.gargantua7.cams.gp.android.ui.event

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.ui.component.bottombar.BottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.ExhibitComposeActivity
import com.gargantua7.cams.gp.android.ui.component.compose.IconRow
import com.gargantua7.cams.gp.android.ui.util.format

class EventActivity : ExhibitComposeActivity<Event>(), BottomBar {

    override val id = "Event"

    override val viewModel by lazy { ViewModelProvider(this).get(EventViewModel::class.java) }

    companion object {
        const val TAG = "EventActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) finish()
        Log.d(TAG, "Ready to onCreate {id = $id}")
        viewModel.id.value = id
        Log.d(TAG, "onCreate Complete")
    }

    @Composable
    override fun onItemRefresh() {
        viewModel.id.value = viewModel.id.value
    }

    @Composable
    override fun exhibitContent(item: Event) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = item.name, fontSize = 24.sp, modifier = Modifier.padding(15.dp, 5.dp))
            Divider()
            IconRow(icon = Icons.Filled.Groups, hint = "Number", text = item.number.toString())
            IconRow(icon = Icons.Filled.Place, hint = "Local", text = item.location)
            IconRow(icon = Icons.Filled.EventNote, hint = "Event Time", text = item.eventTime.format())
            IconRow(
                icon = Icons.Filled.Alarm, hint = "Sign Time",
                text = "${item.startTime.format()} - ${item.endTime.format()}"
            )
            Divider()
            IconRow(icon = Icons.Filled.Info, text = "Event Description")
            Text(
                text = item.content, modifier = Modifier
                    .weight(1f)
                    .padding(15.dp, 0.dp)
            )
        }
    }

    @Composable
    override fun bottomBar() {

    }
}
