package com.gargantua7.cams.gp.android.ui.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.gargantua7.cams.gp.android.logic.model.PersonSearcher
import com.gargantua7.cams.gp.android.logic.model.RepairSearcher
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity
import com.gargantua7.cams.gp.android.ui.component.compose.basicDialog
import com.gargantua7.cams.gp.android.ui.component.topbar.BackTopBar
import com.gargantua7.cams.gp.android.ui.component.topbar.SearchTopBar
import kotlinx.coroutines.CoroutineScope

class SearchActivity : ComposeActivity(), BackTopBar, SearchTopBar {

    companion object {
        const val TAG = "SearchActivity"
    }

    override val viewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    private val personSearchViewModel by lazy { ViewModelProvider(this).get(PersonSearchViewModel::class.java) }

    private val repairSearchViewModel by lazy { ViewModelProvider(this).get(RepairSearchViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "ready to onCreate")
        super.onCreate(savedInstanceState)
        intent.let {
            viewModel.hasPerson = it.getBooleanExtra("person", false)
            it.getSerializableExtra("ps")?.let { ps ->
                personSearchViewModel.searcher.value = (ps as PersonSearcher)
            }
            viewModel.hasRepair = it.getBooleanExtra("repair", false)
            it.getSerializableExtra("rs")?.let { rs ->
                repairSearchViewModel.searcher.value = (rs as RepairSearcher)
            }
            viewModel.picker = it.getBooleanExtra("picker", false)
            it.getStringExtra("key")?.let { s ->
                viewModel.value = s
                if (viewModel.hasPerson) personSearchViewModel.updatePersonSearch(s)
                if (viewModel.hasRepair) repairSearchViewModel.updateRepairSearch(s)
            }
            viewModel.mode = when {
                viewModel.hasPerson && viewModel.hasRepair -> SearchViewModel.PREVIEW_MODE
                viewModel.hasPerson -> SearchViewModel.PERSON_FULL_MODE
                else -> SearchViewModel.REPAIR_FULL_MODE
            }
        }
        Log.d(
            TAG, "onCreate Completed: " +
                    "{hasPerson: ${viewModel.hasPerson}, " +
                    "ps: ${personSearchViewModel.searcher.value}, " +
                    "hasRepair: ${viewModel.hasRepair}, " +
                    "rs: ${repairSearchViewModel.searcher.value}, " +
                    "mode: ${viewModel.mode}"
        )
    }

    @Composable
    override fun contentComponents(scaffoldState: ScaffoldState, scope: CoroutineScope) {
        Column(Modifier.fillMaxSize()) {
            when (viewModel.mode) {
                SearchViewModel.PREVIEW_MODE -> {
                    RepairSearch(repairSearchViewModel).preview()
                    PersonSearch(personSearchViewModel).preview()
                }
                SearchViewModel.PERSON_FULL_MODE -> PersonSearch(personSearchViewModel).draw()
                SearchViewModel.REPAIR_FULL_MODE -> RepairSearch(repairSearchViewModel).draw()
            }
        }
        dialog()
    }

    @Composable
    fun dialog() {
        viewModel.picked?.let { (id, name) ->
            viewModel.showDialog {
                basicDialog(title = "Sure reassign to $name?",
                    onDismissRequest = { viewModel.picked = null },
                    confirmOnClick = {
                        viewModel.picked = null
                        setResult(Activity.RESULT_OK, Intent().putExtra("res", id))
                        finish()
                    }
                )
            }
        }
    }

    override fun onSearch(key: String) {
        if (viewModel.hasPerson) {
            personSearchViewModel.updatePersonSearch(key)
        }
        if (viewModel.hasRepair) {
            repairSearchViewModel.updateRepairSearch(key)
        }
    }

    override fun onBack(context: Context) {
        if (viewModel.mode != SearchViewModel.PREVIEW_MODE && viewModel.hasPerson && viewModel.hasRepair) {
            viewModel.mode = SearchViewModel.PREVIEW_MODE
        } else super.onBack(context)
    }

    override fun onBackPressed() {
        onBack(this)
    }

    override fun onClear() {
        super.onClear()
    }
}
