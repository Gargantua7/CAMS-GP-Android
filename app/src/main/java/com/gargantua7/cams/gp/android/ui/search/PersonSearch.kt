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
class PersonSearch(val viewModel: PersonSearchViewModel) : PersonsPage(), SearchComponent<Person> {

    override val id: String = "PersonSearch"
    override val title = "PersonSearch"

    override fun itemOnClick(item: Person, context: Context) {
        if (context is SearchActivity) {
            context.viewModel.picked = item.username to item.name
        }
    }

    @Composable
    override fun draw() {
        val persons by viewModel.persons.observeAsState()
        persons?.let { swipe(flow = it) }
    }

    @Composable
    override fun preview(items: List<Person>) {

    }
}
