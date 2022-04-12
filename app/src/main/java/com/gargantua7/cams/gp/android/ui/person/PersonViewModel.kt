package com.gargantua7.cams.gp.android.ui.person

import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.logic.model.RepairSearcher
import com.gargantua7.cams.gp.android.logic.paging.RepairPagingSource
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ExhibitComposeViewModel
import kotlinx.coroutines.Dispatchers

/**
 * @author Gargantua7
 */
class PersonViewModel : ExhibitComposeViewModel<Person, String>() {

    override suspend fun getItem(id: String) = PersonRepository.getPersonByUsername(id)

    val repairs = Transformations.switchMap(id) {
        liveData(Dispatchers.IO) {
            if (it == null) emit(null)
            else emit(
                Pager(PagingConfig(pageSize = 10)) {
                    RepairPagingSource(
                        RepairSearcher(initiator = it)
                    )
                }.flow.cachedIn(viewModelScope)
            )
        }
    }
}
