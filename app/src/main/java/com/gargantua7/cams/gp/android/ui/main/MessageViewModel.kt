package com.gargantua7.cams.gp.android.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.logic.model.LocalMsg
import com.gargantua7.cams.gp.android.logic.paging.MsgPreviewPagingSource
import com.gargantua7.cams.gp.android.ui.component.page.ListPage

/**
 * @author Gargantua7
 */
class MessageViewModel : ListPage.ListPageViewModel<LocalMsg>() {

    val messages = Pager(PagingConfig(pageSize = 10)) {
        MsgPreviewPagingSource()
    }.flow.cachedIn(viewModelScope)


}
