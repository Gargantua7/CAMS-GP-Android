package com.gargantua7.cams.gp.android.ui.component.page

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Lens
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.ui.repair.RepairActivity
import com.gargantua7.cams.gp.android.ui.util.toIntuitive

/**
 * @author Gargantua7
 */
abstract class RepairsPage : ListPage<Repair>() {

    override fun itemOnClick(repair: Repair, context: Context) {
        Intent(context, RepairActivity::class.java).let {
            it.putExtra("id", repair.id)
            context.startActivity(it)
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun listItem(repair: Repair) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .padding(5.dp, 2.5.dp)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(20.dp)
                ),
            onClick = { itemOnClick(repair, context) }
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .padding(15.dp, 10.dp, 15.dp, 0.dp)
                ) {
                    Text(
                        text = repair.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                    Row {
                        Text(
                            text = repair.initiator.name,
                            fontSize = 10.sp,
                            color = MaterialTheme.colors.onBackground
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = repair.updateTime.toIntuitive(),
                            fontSize = 10.sp,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                    Text(
                        text = repair.content,
                        fontSize = 15.sp,
                        color = MaterialTheme.colors.onBackground,
                        maxLines = 3,
                        modifier = Modifier.padding(0.dp, 5.dp)
                    )

                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        onClick = {
                            Intent(context, RepairActivity::class.java).let {
                                it.putExtra("id", repair.id)
                                it.putExtra("reply", true)
                                context.startActivity(it)
                            }
                        },
                        elevation = 0.dp,
                        modifier = Modifier
                            .weight(0.5f)
                            .height(40.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(15.dp))
                            Icon(
                                Icons.Filled.Chat, "Chat",
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.height(20.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = repair.reply.toString(),
                                fontSize = 16.sp,
                                color = MaterialTheme.colors.onBackground,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = if (repair.state) "OPEN" else "CLOSE",
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        Icons.Filled.Lens,
                        "Lens",
                        tint = if (repair.state) Color.Green else Color.Red,
                        modifier = Modifier.size(11.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
        }
    }


}
