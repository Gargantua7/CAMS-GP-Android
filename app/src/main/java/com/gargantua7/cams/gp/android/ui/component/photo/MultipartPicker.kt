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
interface MultipartPicker {

    val viewModel: ViewModel

    val multipartPicker
        @Composable get() = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents(),
        ) {
            if (this is Activity) {
                it.forEach { uri ->
                    contentResolver.openFileDescriptor(uri, "r")?.use { pfd ->
                        BitmapFactory.decodeFileDescriptor(pfd.fileDescriptor)
                    }?.let { p -> viewModel.pics.add(p) }
                }
            }
        }

    interface ViewModel {

        val pics: MutableCollection<Bitmap>

    }

}
