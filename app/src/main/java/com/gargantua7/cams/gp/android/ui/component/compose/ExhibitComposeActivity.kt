package com.gargantua7.cams.gp.android.ui.component.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.ui.component.swipeable.Swipeable
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.repair.RepairViewModel
import com.google.accompanist.swiperefresh.SwipeRefreshState
import kotlinx.coroutines.launch

/**
 * @author Gargantua7
 */
abstract class ExhibitComposeActivity<E : Any> : ComposeActivity(), Swipeable, BackTopBar {

    abstract val id: String

    abstract override val viewModel: ExhibitComposeViewModel<E, *>

    @Composable
    abstract fun onItemRefresh()

    @Composable
    abstract fun exhibitContent(item: E)

    @Composable
    override fun swipeContent(refreshState: SwipeRefreshState) {
        refreshState.isRefreshing = viewModel.fresh
        val item by viewModel.item.observeAsState()
        if (viewModel.fresh) {
            onItemRefresh()
            viewModel.fresh = false
        }
        item?.let {
            exhibitContent(it)
        } ?: errorPage()
    }

    override fun onRefresh() {
        viewModel.fresh = true
    }

    @Composable
    override fun RowScope.coreComponents() {
        Text(
            text = id,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

    @Composable
    fun errorPage() {
        val vm = viewModel(RepairViewModel::class.java)
        val scope = rememberCoroutineScope()
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (vm.loading) {
                CircularProgressIndicator()
            } else if (vm.networkError) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = {
                                scope.launch {
                                    vm.id.value?.let { vm.get(it) }
                                }
                            })
                ) {
                    Icon(
                        Icons.Filled.WifiOff,
                        "NetworkError",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = stringResource(R.string.network_error),
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = stringResource(R.string.press_retry),
                        color = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.size(48.dp))
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        Icons.Filled.SearchOff,
                        "Not Found",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Resource Not Found",
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = "It may have been Removed",
                        color = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        }
    }

}
