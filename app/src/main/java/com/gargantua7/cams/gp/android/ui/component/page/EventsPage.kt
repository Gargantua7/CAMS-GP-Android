package com.gargantua7.cams.gp.android.ui.component.page

import android.content.Context
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.ui.util.format
import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
abstract class EventsPage : ListPage<Event>() {

    override fun itemOnClick(event: Event, context: Context) {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun listItem(event: Event) {
        val live = LocalDateTime.now() in event.startTime..event.endTime
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
                            text = "Event Start At:" + event.eventTime.format(),
                            fontSize = 10.sp,
                            color = MaterialTheme.colors.onBackground
                        )
                        if (!live) {
                            Text(
                                text = "Sign Start At:" + event.startTime.format(),
                                fontSize = 10.sp,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        Text(
                            text = "Sign End At:" + event.endTime.format(),
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
                                    LocalDateTime.now() < event.startTime -> "WAITING"
                                    LocalDateTime.now() in event.startTime..event.eventTime -> "SIGNING"
                                    else -> "ENDING"
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
