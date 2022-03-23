package com.gargantua7.cams.gp.android.ui.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * @author Gargantua7
 */
class SignInViewModel: ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisibility by mutableStateOf(false)
}
