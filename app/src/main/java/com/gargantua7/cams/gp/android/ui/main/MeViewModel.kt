package com.gargantua7.cams.gp.android.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * @author Gargantua7
 */
class MeViewModel : ViewModel() {

    var logoutDialog by mutableStateOf(false)

}
