package com.gargantua7.cams.gp.android.ui.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.ui.component.page.PersonsPage
import kotlinx.coroutines.flow.Flow

/**
 * @author Gargantua7
 */
class EventRegisteredPersonsPage(override val viewModel: EventRegisteredViewModel) : PersonsPage(viewModel) {

    override val id = "RegisteredPersons"

    override val title = "Registered Persons"

    override lateinit var lazyItems: Flow<PagingData<Person>>

    @Composable
    override fun draw() {
        val persons by viewModel(EventRegisteredViewModel::class.java).persons.observeAsState()
        persons?.let {
            lazyItems = it
            swipe()
        }
    }


}
