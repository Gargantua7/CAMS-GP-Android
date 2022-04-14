package com.gargantua7.cams.gp.android.ui.component.photo

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

/**
 * @author Gargantua7
 */
interface Picker {

    val viewModel: ViewModel

    val picker
        @Composable get() = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri ->
            if (this is Activity) {
                if (uri != null) {
                    contentResolver.openFileDescriptor(uri, "r")?.use { pfd ->
                        BitmapFactory.decodeFileDescriptor(pfd.fileDescriptor)
                    }?.let { p -> viewModel.pic = p }
                }
            }
        }

    interface ViewModel {

        var pic: Bitmap

    }

}
