package com.gargantua7.cams.gp.android.ui.event

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.ui.component.bottombar.BottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.ExhibitComposeActivity
import com.gargantua7.cams.gp.android.ui.component.compose.IconRow
import com.gargantua7.cams.gp.android.ui.person.SignInActivity
import com.gargantua7.cams.gp.android.ui.util.format
import java.time.LocalDateTime
import java.time.ZoneId

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
        val item by viewModel.item.observeAsState()
        var now by remember { mutableStateOf(LocalDateTime.now(ZoneId.of("Asia/Shanghai"))) }

        DisposableEffect(now) {
            val handler = Handler(Looper.getMainLooper())
            val runnable = {
                now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"))
            }
            handler.postDelayed(runnable, 1_000)
            onDispose { handler.removeCallbacks(runnable) }
        }

        item?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White,
                        disabledBackgroundColor = MaterialTheme.colors.secondary
                    ),
                    enabled = now in it.startTime..it.endTime && !viewModel.success,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 20.dp, 10.dp),
                    contentPadding = PaddingValues(10.dp),
                    onClick = {
                        if (CAMSApplication.session.value == null)
                            startActivityWithMsgResult(Intent(this@EventActivity, SignInActivity::class.java))
                    }
                ) {
                    val (icon, text) = when {
                        viewModel.success -> Icons.Filled.EventAvailable to "Registered"
                        now in it.startTime..it.endTime -> Icons.Filled.EditCalendar to "Register now"
                        else -> Icons.Filled.EventBusy to "Out of time"
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = icon, contentDescription = "Login")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = text)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
