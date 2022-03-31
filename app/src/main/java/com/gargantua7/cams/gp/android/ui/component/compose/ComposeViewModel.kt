package com.gargantua7.cams.gp.android.ui.component.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * @author Gargantua7
 */
abstract class ComposeViewModel : ViewModel() {

    var errorMsg by mutableStateOf<String?>(null)

    var loading by mutableStateOf(false)
}
