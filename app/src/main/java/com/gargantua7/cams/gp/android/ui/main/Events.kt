package com.gargantua7.cams.gp.android.ui.main

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.paging.PagingData
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.page.EventsPage
import com.gargantua7.cams.gp.android.ui.component.page.NavPage
import com.gargantua7.cams.gp.android.ui.event.NewEventActivity
import com.gargantua7.cams.gp.android.ui.util.stringResource
import kotlinx.coroutines.flow.Flow

/**
 * @author Gargantua7
 */
class Events(override val viewModel: EventViewModel) : EventsPage(viewModel), NavPage {

    override val id: String = "Event"
    override val title = stringResource(R.string.event)
    override val icon = Icons.Filled.Celebration


    override lateinit var lazyItems: Flow<PagingData<Event>>

    @Composable
    override fun draw() {
        lazyItems = viewModel.events
        swipe()
    }

    @Composable
    override fun fab() {
        val user by CAMSApplication.user.observeAsState()
        if ((user?.permission ?: -1) >= 4) {
            fab(icons = Icons.Filled.Add)
        }
    }

    override fun fabOnClick(context: ComposeActivity) {
        context.startActivityWithMsgResult(Intent(context, NewEventActivity::class.java))
    }


}
