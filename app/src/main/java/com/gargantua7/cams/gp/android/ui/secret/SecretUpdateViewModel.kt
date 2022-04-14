package com.gargantua7.cams.gp.android.ui.secret

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.exception.BadRequestException
import com.gargantua7.cams.gp.android.logic.model.SecretUpdate
import com.gargantua7.cams.gp.android.logic.repository.SecretRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import com.gargantua7.cams.gp.android.ui.util.matchPassword
import kotlinx.coroutines.launch
import java.net.UnknownHostException

/**
 * @author Gargantua7
 */
class SecretUpdateViewModel : ComposeViewModel() {

    var origin by mutableStateOf("")

    var new by mutableStateOf("")

    var confirm by mutableStateOf("")

    var passwordVisibility by mutableStateOf(false)

    var success by mutableStateOf(false)

    fun submit() {
        if (matchPassword(new) && new == confirm) {
            viewModelScope.launch {
                val res = SecretRepository.updateSecret(SecretUpdate(origin, new))
                if (res.isSuccess) {
                    success = true
                } else {
                    showSnackBar(
                        when (res.exceptionOrNull()) {
                            is BadRequestException -> "Wrong Password Format"
                            is AuthorizedException -> "Wrong Old Password"
                            is UnknownHostException -> "Network Error"
                            else -> "Unknown Error"
                        }
                    )
                }
            }
        }
    }

}
