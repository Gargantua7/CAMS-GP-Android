package com.gargantua7.cams.gp.android.ui.repair

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.exception.BadRequestException
import com.gargantua7.cams.gp.android.logic.model.NewRepair
import com.gargantua7.cams.gp.android.logic.repository.RepairRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import com.gargantua7.cams.gp.android.ui.component.photo.MultipartPicker
import com.gargantua7.cams.gp.android.ui.util.encodeImage
import kotlinx.coroutines.launch

/**
 * @author Gargantua7
 */
class NewRepairViewModel : ComposeViewModel(), MultipartPicker.ViewModel {

    var title by mutableStateOf("")
    var content by mutableStateOf("")

    var private by mutableStateOf(false)

    var success by mutableStateOf(false)

    override val pics = mutableStateListOf<Bitmap>()

    fun send() {
        if (title.isBlank()) {
            showSnackBar("Title must be non-empty")
            return
        }
        if (title.length > 20) {
            showSnackBar("Title too long")
            return
        }
        viewModelScope.launch {
            val ct = buildString {
                append(content)
                append("\n<img>")
                pics.forEachIndexed { i, it ->
                    append(encodeImage(it))
                    if (i != pics.lastIndex) append(",")
                }
                append("<\\img>")
            }
            val res = RepairRepository.createNewRepair(NewRepair(title, ct, private))
            if (res.isSuccess) {
                success = true
            } else {
                showSnackBar(
                    when (res.exceptionOrNull()) {
                        is BadRequestException -> "Title Too Long Or Too Short"
                        is AuthorizedException -> "Authorized"
                        else -> "Unknown Exception"
                    }
                )
            }
        }
    }
}
