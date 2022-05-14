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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.ui.component.bottombar.BottomBar
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.compose.ExhibitComposeActivity
import com.gargantua7.cams.gp.android.ui.component.compose.IconRow
import com.gargantua7.cams.gp.android.ui.component.fab.FAB
import com.gargantua7.cams.gp.android.ui.secret.SignInActivity
import com.gargantua7.cams.gp.android.ui.util.format
import com.gargantua7.cams.gp.android.ui.util.nowForShanghai
import java.time.LocalDateTime
import java.time.ZoneId

class EventActivity : ExhibitComposeActivity<Event>(), BottomBar, FAB {

    override val id = com.gargantua7.cams.gp.android.ui.util.stringResource(R.string.event)

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
            IconRow(icon = Icons.Filled.Groups, hint = stringResource(R.string.e_number), text = item.number.toString())
            IconRow(icon = Icons.Filled.Place, hint = stringResource(R.string.e_local), text = item.location)
            IconRow(
                icon = Icons.Filled.DateRange,
                hint = stringResource(R.string.e_time),
                text = item.eventTime.format()
            )
            IconRow(
                icon = Icons.Filled.Alarm, hint = stringResource(R.string.e_sign_time),
                text = "${item.startTime.format()} - ${item.endTime.format()}"
            )
            Divider()
            IconRow(icon = Icons.Filled.Info, text = stringResource(R.string.e_des))
            Text(
                text = item.content, modifier = Modifier
                    .weight(1f)
                    .padding(15.dp, 0.dp)
            )
        }
    }

    @Composable
    override fun fab() {
        val user by CAMSApplication.user.observeAsState()
        val event by viewModel.item.observeAsState()
        if ((user?.permission ?: -1) >= 4) {
            event?.let {
                if (it.startTime <= nowForShanghai())
                    fab(icons = Icons.Filled.PlaylistAddCheck)
            }
        }
    }

    override fun fabOnClick(context: ComposeActivity) {
        startActivity(Intent(this, EventStatisticsActivity::class.java).apply {
            putExtra("id", viewModel.id.value)
        })
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
                        else
                            viewModel.signEvent()
                    }
                ) {
                    val (icon, text) = when {
                        viewModel.success -> Icons.Filled.EventAvailable to stringResource(id = R.string.e_reged)
                        now in it.startTime..it.endTime -> Icons.Filled.EditCalendar to stringResource(id = R.string.e_reg_now)
                        else -> Icons.Filled.EventBusy to stringResource(R.string.e_out)
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
