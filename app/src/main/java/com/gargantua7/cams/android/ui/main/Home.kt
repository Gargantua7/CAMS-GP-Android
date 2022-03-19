package com.gargantua7.cams.android.ui.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lens
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gargantua7.cams.android.CAMSApplication
import com.gargantua7.cams.android.R
import com.gargantua7.cams.android.logic.model.Repair
import java.time.format.DateTimeFormatter

/**
 * @author Gargantua7
 */
object Home : Page() {

    override val title = CAMSApplication.context.getString(R.string.home)
    override val icon = Icons.Filled.Home

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun draw() {

        val repairs = viewModel(RepairViewModel::class.java).repairs.collectAsLazyPagingItems()

        when (repairs.loadState.refresh) {
            is LoadState.NotLoading -> LazyColumn {
                items(repairs) { repair ->
                    repair?.let { repairItem(repair = it) }
                }
                Log.d("repair ui state", repairs.loadState.append.toString())
                when (repairs.loadState.append) {
                    is LoadState.Loading -> item { loadItem() }
                    is LoadState.Error -> item { errorItem { repairs.retry() } }
                    else -> Unit
                }
            }
            is LoadState.Loading -> loadingPage()
            is LoadState.Error -> errorPage { repairs.refresh() }
            else -> Unit
        }

    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun repairItem(repair: Repair) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(20.dp)
                ),
            onClick = {

            }
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .padding(15.dp, 15.dp, 15.dp, 0.dp)
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
                            text = repair.updateTime.format(DateTimeFormatter.ofPattern("yy/MM/dd hh:mm:ss")),
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
                        onClick = { /*TODO*/ },
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
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        Icons.Filled.Lens,
                        "Lens",
                        tint = if (repair.state) Color.Green else Color.Red,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
        }

    }

    @Composable
    fun loadingPage() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }
    }

    @Composable
    fun loadItem() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = stringResource(R.string.loading), color = MaterialTheme.colors.onSurface)
        }
    }

    @Composable
    fun errorPage(onClick: () -> Unit) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = onClick)
        ) {
            Icon(
                Icons.Filled.WifiOff,
                "NetworkError",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = stringResource(R.string.press_retry), color = MaterialTheme.colors.onSurface)
            Spacer(modifier = Modifier.size(48.dp))
        }
    }

    @Composable
    fun errorItem(onClick: () -> Unit) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = onClick)
        ) {
            Text(text = stringResource(R.string.network_error), color = MaterialTheme.colors.onSurface)
            Text(text = stringResource(R.string.press_retry), color = MaterialTheme.colors.onSurface)
        }
    }

}
