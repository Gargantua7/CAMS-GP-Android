package com.gargantua7.cams.gp.android.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.logic.paging.EventPagingSource
import com.gargantua7.cams.gp.android.ui.component.page.ListPage

/**
 * @author Gargantua7
 */
class EventViewModel : ListPage.ListPageViewModel<Event>() {

    val events = Pager(PagingConfig(pageSize = 10)) {
        EventPagingSource()
    }.flow.cachedIn(viewModelScope)

}
