package com.gargantua7.cams.gp.android.ui.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.logic.paging.EventSignedPagingSource
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import kotlinx.coroutines.Dispatchers

/**
 * @author Gargantua7
 */
class EventRegisteredViewModel : ComposeViewModel() {

    val id = MutableLiveData<Long>()

    val persons = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            emit(
                Pager(PagingConfig(pageSize = 10)) {
                    EventSignedPagingSource(it)
                }.flow.cachedIn(viewModelScope)
            )
        }
    }

}
