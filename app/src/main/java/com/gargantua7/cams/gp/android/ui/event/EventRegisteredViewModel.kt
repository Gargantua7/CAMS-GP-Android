package com.gargantua7.cams.gp.android.ui.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.logic.paging.EventSignedPagingSource
import com.gargantua7.cams.gp.android.ui.component.page.ListPage
import kotlinx.coroutines.Dispatchers

/**
 * @author Gargantua7
 */
class EventRegisteredViewModel : ListPage.ListPageViewModel<Person>() {

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
