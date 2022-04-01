package com.gargantua7.cams.gp.android.ui.component.swipeable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * @author Gargantua7
 */
interface Swipeable {

    @Composable
    fun swipe() {
        val refreshState = rememberSwipeRefreshState(false)
        SwipeRefresh(
            state = refreshState,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state, trigger,
                    contentColor = MaterialTheme.colors.primary
                )
            },
            onRefresh = ::onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            swipeContent(refreshState)
        }
    }

    @Composable
    fun swipeContent(refreshState: SwipeRefreshState)

    fun onRefresh()

}
