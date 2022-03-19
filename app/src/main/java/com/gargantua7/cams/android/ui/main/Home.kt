package com.gargantua7.cams.android.ui.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gargantua7.cams.android.CAMSApplication
import com.gargantua7.cams.android.R
import com.gargantua7.cams.android.logic.model.Repair
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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
        val isRefresh = rememberSwipeRefreshState(false)

        SwipeRefresh(state = isRefresh,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state, trigger,
                    contentColor = MaterialTheme.colors.primary
                )
            },
            onRefresh = { repairs.refresh() }) {
            isRefresh.isRefreshing = repairs.loadState.refresh is LoadState.Loading && repairs.itemCount > 0
            when (repairs.loadState.refresh) {
                is LoadState.NotLoading -> list(repairs)
                is LoadState.Loading -> {
                    if (repairs.itemCount > 0) list(repairs)
                    else loadingPage()
                }
                is LoadState.Error -> {
                    if (repairs.itemCount > 0) {
                        list(repairs)
                        Toast.makeText(LocalContext.current, stringResource(R.string.network_error), Toast.LENGTH_SHORT)
                            .show()
                    }
                    else errorPage { repairs.refresh() }
                }
            }
        }
    }

    @Composable
    override fun fab() {
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Add, "add")
        }
    }

    @Composable
    fun list(repairs: LazyPagingItems<Repair>) {
        LazyColumn {
            item { Spacer(modifier = Modifier.height(2.5.dp)) }
            items(repairs) { repair ->
                repair?.let { repairItem(repair = it) }
            }
            Log.d("repair ui state", repairs.loadState.append.toString())
            when (repairs.loadState.append) {
                is LoadState.Loading -> item { loadingItem() }
                is LoadState.Error -> item { errorItem { repairs.retry() } }
                else -> Unit
            }
            item { Spacer(modifier = Modifier.height(2.5.dp)) }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun repairItem(repair: Repair) {
        Card(
            modifier = Modifier
                .padding(5.dp, 2.5.dp)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(20.dp)
                ),
            onClick = {
                /* TODO */
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
    fun loadingItem() {
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
            Text(text = stringResource(R.string.network_error), color = MaterialTheme.colors.onSurface)
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
            Text(text = stringResource(R.string.press_retry), color = MaterialTheme.colors.onSurface)
        }
    }

}
