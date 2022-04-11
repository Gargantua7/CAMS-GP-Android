package com.gargantua7.cams.gp.android.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.logic.model.RepairSearcher
import com.gargantua7.cams.gp.android.logic.paging.RepairPagingSource
import com.gargantua7.cams.gp.android.ui.component.page.ListPage

/**
 * @author Gargantua7
 */
class RepairViewModel : ListPage.ListPageViewModel<Repair>() {

    val repairs = Pager(PagingConfig(pageSize = 10)) {
        RepairPagingSource(RepairSearcher())
    }.flow.cachedIn(viewModelScope)

}
