package com.gargantua7.cams.gp.android.ui.main

import android.content.Intent
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.ui.component.page.EventsPage
import com.gargantua7.cams.gp.android.ui.component.page.NavPage
import com.gargantua7.cams.gp.android.ui.event.NewEventActivity
import com.gargantua7.cams.gp.android.ui.util.stringResource

/**
 * @author Gargantua7
 */
object Events : EventsPage(), NavPage {

    override val id: String = "Event"
    override val title = stringResource(R.string.event)
    override val icon = Icons.Filled.Celebration

    @Composable
    override fun draw() {
        items = viewModel(EventViewModel::class.java).events.collectAsLazyPagingItems()
        swipe()
    }

    @Composable
    override fun fab() {
        val user = CAMSApplication.user.observeAsState()
        if ((user.value?.permission ?: -1) >= 4) {
            val context = LocalContext.current as MainActivity
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                onClick = {
                    context.startActivityWithMsgResult(Intent(context, NewEventActivity::class.java))
                }) {
                Icon(Icons.Filled.Add, "add")
            }
        }
    }


}
