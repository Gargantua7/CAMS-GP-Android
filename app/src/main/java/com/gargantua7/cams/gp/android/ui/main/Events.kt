package com.gargantua7.cams.gp.android.ui.main

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.ui.component.page.EventsPage
import com.gargantua7.cams.gp.android.ui.component.page.NavPage
import com.gargantua7.cams.gp.android.ui.util.stringResource

/**
 * @author Gargantua7
 */
object Events : EventsPage(), NavPage {

    override val id: String = "Event"
    override val title = stringResource(R.string.event)
    override val icon = Icons.Filled.Celebration

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun draw() {
        val events = viewModel(EventViewModel::class.java).events
        swipe(events)
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


}
