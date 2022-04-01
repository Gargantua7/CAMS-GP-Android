package com.gargantua7.cams.gp.android.ui.search

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.ui.component.page.PersonsPage

/**
 * @author Gargantua7
 */
class PersonSearch(override val viewModel: PersonSearchViewModel) : PersonsPage(), SearchComponent<Person> {

    override val id: String = "PersonSearch"
    override val title = "PersonSearch"
    override val element = "Person"
    override val mode = SearchViewModel.PERSON_FULL_MODE

    override fun itemOnClick(person: Person, context: Context) {
        if (context is SearchActivity) {
            if (context.viewModel.picker) {
                context.viewModel.picked = person.username to person.name
            } else {
                /** TODO **/
            }
        }
    }

    @Composable
    override fun listItem(person: Person) {
        super.listItem(person)
    }

    @Composable
    override fun draw() {
        val items by viewModel.items.observeAsState()
        items?.let { swipe(flow = it) }
    }
}
