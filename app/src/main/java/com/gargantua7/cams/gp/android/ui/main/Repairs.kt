package com.gargantua7.cams.gp.android.ui.main

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.ui.component.page.NavPage
import com.gargantua7.cams.gp.android.ui.component.page.RepairsPage
import com.gargantua7.cams.gp.android.ui.util.stringResource

/**
 * @author Gargantua7
 */
object Repairs : RepairsPage(), NavPage {

    override val id: String = "Repair"
    override val title = stringResource(R.string.home)
    override val icon = Icons.Filled.Home

    @Composable
    override fun draw() {
        items = viewModel(RepairViewModel::class.java).repairs.collectAsLazyPagingItems()
        swipe()
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
