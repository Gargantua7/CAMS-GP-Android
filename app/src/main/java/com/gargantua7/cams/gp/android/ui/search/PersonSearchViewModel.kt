package com.gargantua7.cams.gp.android.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gargantua7.cams.gp.android.logic.model.PersonSearcher
import com.gargantua7.cams.gp.android.logic.paging.PersonPagingSource
import kotlinx.coroutines.Dispatchers

/**
 * @author Gargantua7
 */
class PersonSearchViewModel : ViewModel() {

    val searcher = MutableLiveData<PersonSearcher>()

    var loading by mutableStateOf(false)

    val persons = Transformations.switchMap(searcher) {
        Log.d("PersonSearch-VM", "Refresh Persons -> $it")
        liveData(Dispatchers.IO) {
            emit(
                Pager(PagingConfig(pageSize = 10)) {
                    PersonPagingSource(it)
                }.flow.cachedIn(viewModelScope)
            )
        }
    }

    fun updatePersonSearch(s: String) {
        searcher.let {
            val (username, name) = if (s.matches(Regex("^[0-9]{12}\$"))) s to "" else "" to s
            it.value = PersonSearcher(
                username = username.ifBlank { null },
                name = name.ifBlank { null },
                sex = it.value?.sex,
                depId = it.value?.depId,
                permissionLevel = it.value?.permissionLevel
            )
        }
    }

}
