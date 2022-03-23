package com.gargantua7.cams.gp.android.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.logic.paging.EventPagingSource

/**
 * @author Gargantua7
 */
class EventViewModel: ViewModel() {

    val events = Pager(PagingConfig(pageSize = 10)) {
            EventPagingSource()
    }.flow.cachedIn(viewModelScope)

}
