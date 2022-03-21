package com.gargantua7.cams.android.ui.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gargantua7.cams.android.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.Flow

/**
 * @author Gargantua7
 */
sealed class PageWithList<E : Any> : Page() {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun swipe(
        flow: Flow<PagingData<E>>,
        filler: @Composable () -> Unit = { Spacer(modifier = Modifier.height(2.5.dp)) },
    ) {
        val items = flow.collectAsLazyPagingItems()
        val isRefresh = rememberSwipeRefreshState(false)

        SwipeRefresh(state = isRefresh,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state, trigger,
                    contentColor = MaterialTheme.colors.primary
                )
            },
            onRefresh = { items.refresh() }) {
            isRefresh.isRefreshing = items.loadState.refresh is LoadState.Loading && items.itemCount > 0
            Log.d("$title Paging Refresh UI State", items.loadState.append.toString())
            when (items.loadState.refresh) {
                is LoadState.NotLoading -> list(items, filler)
                is LoadState.Loading -> {
                    if (items.itemCount > 0) list(items, filler)
                    else loadingPage()
                }
                is LoadState.Error -> {
                    if (items.itemCount > 0) {
                        list(items, filler)
                        Toast.makeText(LocalContext.current, stringResource(R.string.network_error), Toast.LENGTH_SHORT)
                            .show()
                    } else errorPage { items.refresh() }
                }
            }
        }
    }

    @Composable
    fun list(
        items: LazyPagingItems<E>,
        filler: @Composable () -> Unit = { Spacer(modifier = Modifier.height(2.5.dp)) },
    ) {
        LazyColumn {
            item { filler() }
            items(items) { item ->
                item?.let { listItem(it) }
            }
            Log.d("$title Paging Append UI State", items.loadState.append.toString())
            when (items.loadState.append) {
                is LoadState.Loading -> item { loadingItem() }
                is LoadState.Error -> item { errorItem { items.retry() } }
                else -> Unit
            }
            item { filler() }
        }
    }

    @Composable
    abstract fun listItem(item: E)

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

val itemsWithList = listOf(Repairs, Events)
