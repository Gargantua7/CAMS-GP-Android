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

    var hasPerson = false
    var hasRepair = false
    var picker = false

    override var value by mutableStateOf("")
}
