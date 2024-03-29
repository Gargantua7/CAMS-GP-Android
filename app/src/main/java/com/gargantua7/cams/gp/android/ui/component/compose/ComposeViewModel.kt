package com.gargantua7.cams.gp.android.ui.component.compose

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
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

    var dialog by mutableStateOf<(@Composable ComposeViewModel.() -> Unit)?>(null)

    var bitmap by mutableStateOf<Bitmap?>(null)

    fun showSnackBar(text: String) {
        errorMsg = text
    }

    fun showDialog(dialog: @Composable ComposeViewModel.() -> Unit) {
        this.dialog = dialog
    }
}
