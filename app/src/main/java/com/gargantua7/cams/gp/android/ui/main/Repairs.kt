package com.gargantua7.cams.gp.android.ui.main

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.page.NavPage
import com.gargantua7.cams.gp.android.ui.component.page.RepairsPage
import com.gargantua7.cams.gp.android.ui.repair.NewRepairActivity
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
        fab(icons = Icons.Filled.Add)
    }

    override fun fabOnClick(context: ComposeActivity) {
        context.startActivityWithMsgResult(Intent(context, NewRepairActivity::class.java))
    }

}
