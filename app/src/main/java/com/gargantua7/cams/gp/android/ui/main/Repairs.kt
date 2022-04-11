package com.gargantua7.cams.gp.android.ui.main

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import com.gargantua7.cams.gp.android.R
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.page.NavPage
import com.gargantua7.cams.gp.android.ui.component.page.RepairsPage
import com.gargantua7.cams.gp.android.ui.repair.NewRepairActivity
import com.gargantua7.cams.gp.android.ui.util.stringResource
import kotlinx.coroutines.flow.Flow

/**
 * @author Gargantua7
 */
class Repairs(override val viewModel: RepairViewModel) : RepairsPage(viewModel), NavPage {

    override val id: String = "Repair"
    override val title = stringResource(R.string.home)
    override val icon = Icons.Filled.Home

    override lateinit var lazyItems: Flow<PagingData<Repair>>

    @Composable
    override fun draw() {
        lazyItems = viewModel.repairs
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
