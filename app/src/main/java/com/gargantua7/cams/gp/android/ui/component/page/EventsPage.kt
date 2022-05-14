package com.gargantua7.cams.gp.android.ui.component.page

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lens
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.ui.event.EventActivity
import com.gargantua7.cams.gp.android.ui.util.format
import com.gargantua7.cams.gp.android.ui.util.nowForShanghai

/**
 * @author Gargantua7
 */
abstract class EventsPage(viewModel: ListPageViewModel<Event>) : ListPage<Event>(viewModel) {

    override fun itemOnClick(event: Event, context: Context) {
        Intent(context, EventActivity::class.java).let {
            it.putExtra("id", event.id)
            context.startActivity(it)
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun listItem(event: Event) {
        val live = nowForShanghai() in event.startTime..event.endTime
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .padding(5.dp, 2.5.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(20.dp),
                ),
            onClick = { itemOnClick(event, context) }
        ) {
            Column(
                modifier = Modifier.padding(15.dp, 10.dp)
            ) {
                Text(
                    text = event.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "${stringResource(id = R.string.e_time)}:" + event.eventTime.format(),
                            fontSize = 10.sp,
                            color = MaterialTheme.colors.onBackground
                        )
                        if (!live) {
                            Text(
                                text = "${stringResource(R.string.e_start)}:" + event.startTime.format(),
                                fontSize = 10.sp,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        Text(
                            text = "${stringResource(R.string.e_end)}:" + event.endTime.format(),
                            fontSize = 10.sp,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = when {
                                    nowForShanghai() < event.startTime -> stringResource(R.string.e_waiting)
                                    nowForShanghai() in event.startTime..event.endTime -> stringResource(R.string.e_signing)
                                    else -> stringResource(id = R.string.e_ending)
                                },
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.onBackground
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Icon(
                                Icons.Filled.Lens,
                                "Lens",
                                tint = if (live) Color.Green else Color.Red,
                                modifier = Modifier.size(11.dp)
                            )

                        }
                    }
//                    Spacer(modifier = Modifier.width(15.dp))
                }

            }
        }
    }

}
