package com.gargantua7.cams.gp.android.ui.event

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.compose.IconRow
import com.gargantua7.cams.gp.android.ui.component.fab.FAB
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import kotlinx.coroutines.CoroutineScope

class EventStatisticsActivity : ComposeActivity(), BackTopBar, FAB {

    override val viewModel by lazy { ViewModelProvider(this).get(EventStatisticsViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) finish()
        else {
            viewModel.id.value = id
        }
    }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = "报名统计",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

    @Composable
    override fun fab() {
        fab(icons = Icons.Filled.PlaylistAddCheck)
    }

    override fun fabOnClick(context: ComposeActivity) {
        startActivity(Intent(this, EventRegisteredActivity::class.java).apply {
            putExtra("id", viewModel.id.value)
        })
    }


    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        Column {
            val c by viewModel.count.observeAsState()
            c?.let { count ->
                IconRow(icon = Icons.Filled.Add, hint = "报名人数", text = count.toString())
                Spacer(modifier = Modifier.height(10.dp))
                val sex by viewModel.sex.observeAsState()
                sex?.let {
                    IconRow(icon = Icons.Filled.Wc, text = "性别")
                    it.forEach { (k, v) -> Line(title = k, value = v, max = count) }
                }
                val time by viewModel.time.observeAsState()
                time?.let {
                    IconRow(icon = Icons.Filled.Schedule, text = "报名时间")
                    it.forEach { (k, v) -> Line(title = k, value = v, max = count) }
                }
                val collage by viewModel.collage.observeAsState()
                collage?.let {
                    IconRow(icon = Icons.Filled.Apartment, text = "学院")
                    it.forEach { (k, v) -> Line(title = k, value = v, max = count) }
                }
                val major by viewModel.major.observeAsState()
                major?.let {
                    IconRow(icon = Icons.Filled.Workspaces, text = "专业")
                    it.forEach { (k, v) -> Line(title = k, value = v, max = count) }
                }
            }

        }

    }

    @Composable
    fun Line(title: String, value: Int, max: Int) {
        Column(Modifier.background(MaterialTheme.colors.surface)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = value.toString())
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier
                        .weight(value.toFloat() / max.toFloat())
                        .height(15.dp),
                    color = MaterialTheme.colors.primary
                )
                if (1f - (value.toFloat() / max.toFloat()) > 0f) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f - (value.toFloat() / max.toFloat()))
                    )
                }
                Text(text = (value.toFloat() / max.toFloat() * 100).toInt().toString() + "%")
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
    }


}
