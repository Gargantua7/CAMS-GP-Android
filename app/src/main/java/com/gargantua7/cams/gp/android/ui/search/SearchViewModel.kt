package com.gargantua7.cams.gp.android.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * @author Gargantua7
 */
class SearchViewModel : ViewModel() {

    var hasPerson = false
    var hasRepair = false
    var picker = false

    var errorMsg by mutableStateOf<String?>(null)

    var value by mutableStateOf("")
}
