package com.gargantua7.cams.gp.android.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.logic.model.PersonSearcher
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.component.topbar.SearchTopBar
import kotlinx.coroutines.CoroutineScope

class SearchActivity : ComposeActivity(), BackTopBar, SearchTopBar {

    override val viewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    private val personSearchViewModel by lazy { ViewModelProvider(this).get(PersonSearchViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.let {
            viewModel.hasPerson = it.getBooleanExtra("person", false)
            it.getSerializableExtra("ps")?.let { ps ->
                Log.d("ps", ps.toString())
                personSearchViewModel.searcher.value = (ps as PersonSearcher)
                Log.d("ob", personSearchViewModel.searcher.value?.toString() ?: "")
            }
            viewModel.hasRepair = it.getBooleanExtra("repair", false)
            //it.getSerializableExtra("rs").let { rs -> viewModel.repairSearcher = rs as RepairSearcher }
            viewModel.picker = it.getBooleanExtra("picker", false)
        }
    }

    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        Column(Modifier.fillMaxSize()) {
            PersonSearch(personSearchViewModel).draw()
        }
        dialog()
    }

    @Composable
    fun dialog() {
        viewModel.picked?.let { (id, name) ->
            AlertDialog(
                onDismissRequest = { viewModel.picked = null },
                title = { Text(text = "Sure reassign to $name?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.picked = null
                        setResult(Activity.RESULT_OK, Intent().putExtra("res", id))
                        finish()
                    }) {
                        Text(text = "Sure")
                    }
                },
            )
        }
    }

    override fun onSearch(key: String) {
        if (viewModel.hasPerson) {
            personSearchViewModel.updatePersonSearch(viewModel.value)
        }
    }

    override fun onClear() {
        super.onClear()
        personSearchViewModel.updatePersonSearch("")
    }
}
