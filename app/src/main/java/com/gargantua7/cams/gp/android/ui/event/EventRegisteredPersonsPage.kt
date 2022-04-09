package com.gargantua7.cams.gp.android.ui.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.gargantua7.cams.gp.android.ui.component.page.PersonsPage

/**
 * @author Gargantua7
 */
object EventRegisteredPersonsPage : PersonsPage() {

    override val id = "RegisteredPersons"

    override val title = "Registered Persons"

    @Composable
    override fun draw() {
        val persons by viewModel(EventRegisteredViewModel::class.java).persons.observeAsState()
        persons?.let {
            items = it.collectAsLazyPagingItems()
            swipe()
        }
    }


}
