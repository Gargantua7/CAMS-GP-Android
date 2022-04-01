package com.gargantua7.cams.gp.android.ui.search

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.ui.component.page.RepairsPage
import com.gargantua7.cams.gp.android.ui.repair.RepairActivity

/**
 * @author Gargantua7
 */
class RepairSearch(override val viewModel: RepairSearchViewModel) : RepairsPage(), SearchComponent<Repair> {

    override val id: String = "RepairSearch"
    override val title = "RepairSearch"
    override val element = "Repair"
    override val mode = SearchViewModel.REPAIR_FULL_MODE

    override fun itemOnClick(repair: Repair, context: Context) {
        if (context is SearchActivity) {
            if (context.viewModel.picker) {
                context.viewModel.picked = repair.id.toString() to repair.title
            } else {
                context.startActivity(Intent(context, RepairActivity::class.java).putExtra("id", repair.id))
            }
        }
    }

    @Composable
    override fun listItem(repair: Repair) {
        super.listItem(repair)
    }

    @Composable
    override fun draw() {
        val items by viewModel.items.observeAsState()
        items?.let { swipe(flow = it) }
    }
}
