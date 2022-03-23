package com.gargantua7.cams.gp.android.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gargantua7.cams.gp.android.logic.model.Person

/**
 * @author Gargantua7
 */
class MainViewModel: ViewModel() {

    var select by mutableStateOf(0)

    var value by mutableStateOf("")

    var user by mutableStateOf<Person?>(null)

}