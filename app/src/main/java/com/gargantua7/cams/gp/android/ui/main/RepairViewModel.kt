package com.gargantua7.cams.gp.android.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.logic.model.RepairSearcher
import com.gargantua7.cams.gp.android.logic.paging.RepairPagingSource

/**
 * @author Gargantua7
 */
class RepairViewModel: ViewModel() {

    val repairs = Pager(PagingConfig(pageSize = 10)) {
        RepairPagingSource(RepairSearcher())
    }.flow.cachedIn(viewModelScope)

}
