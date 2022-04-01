package com.gargantua7.cams.gp.android.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import com.gargantua7.cams.gp.android.ui.component.topbar.SearchTopBar

/**
 * @author Gargantua7
 */
class SearchViewModel : ComposeViewModel(), SearchTopBar.SearchTopBarViewModel {

    var hasPerson by mutableStateOf(false)
    var hasRepair by mutableStateOf(false)
    var picker = false

    override var value by mutableStateOf("")

    var picked by mutableStateOf<Pair<String, String>?>(null)

    var mode by mutableStateOf(PREVIEW_MODE)

    companion object {
        const val PREVIEW_MODE = 0
        const val REPAIR_FULL_MODE = 1
        const val PERSON_FULL_MODE = 2
    }
}
