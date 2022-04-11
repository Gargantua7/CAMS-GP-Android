package com.gargantua7.cams.gp.android.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.logic.model.RepairSearcher
import com.gargantua7.cams.gp.android.logic.paging.RepairPagingSource
import com.gargantua7.cams.gp.android.logic.repository.RepairRepository
import com.gargantua7.cams.gp.android.ui.component.page.ListPage
import kotlinx.coroutines.Dispatchers

/**
 * @author Gargantua7
 */
class RepairSearchViewModel : ListPage.ListPageViewModel<Repair>(), SearchComponent.SearchComponentViewModel<Repair> {

    override val searcher = MutableLiveData<RepairSearcher>()

    override val items = Transformations.switchMap(searcher) {
        Log.d("RepairSearch-VM", "Refresh Repairs -> $it")
        liveData(Dispatchers.IO) {
            emit(
                Pager(PagingConfig(pageSize = 10)) {
                    RepairPagingSource(it)
                }.flow.cachedIn(viewModelScope)
            )
        }
    }
    override val preview = Transformations.switchMap(searcher) {
        liveData(Dispatchers.IO) {
            RepairRepository.searchRepair(0, it).let {
                emit(if (it.isSuccess) it.getOrThrow().let { list -> list.subList(0, minOf(list.size, 3)) } else null)
            }
        }
    }

    fun updateRepairSearch(s: String) {
        searcher.let {
            it.value = RepairSearcher(
                id = it.value?.id,
                keyword = s,
                initiator = it.value?.initiator,
                principal = it.value?.principal,
                state = it.value?.state,
                unassigned = it.value?.unassigned ?: false,
            )
        }
    }
}
