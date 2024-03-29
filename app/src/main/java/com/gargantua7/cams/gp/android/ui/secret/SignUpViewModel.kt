package com.gargantua7.cams.gp.android.ui.secret

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.gargantua7.cams.gp.android.logic.exception.BadRequestException
import com.gargantua7.cams.gp.android.logic.exception.ForbiddenException
import com.gargantua7.cams.gp.android.logic.exception.NotFoundException
import com.gargantua7.cams.gp.android.logic.model.SignUp
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import com.gargantua7.cams.gp.android.logic.repository.SecretRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import com.gargantua7.cams.gp.android.ui.person.PersonBaseInfoViewModel
import com.gargantua7.cams.gp.android.ui.util.matchUsername
import kotlinx.coroutines.launch

/**
 * @author Gargantua7
 */
class SignUpViewModel : ComposeViewModel() {

    var username by mutableStateOf("")

    var usernameAvailable by mutableStateOf<Boolean?>(null)

    val availableMap = HashMap<String, Boolean>()

    var passwordVisibility by mutableStateOf(false)

    var password by mutableStateOf("")

    var confirmPassword by mutableStateOf("")

    val baseInfoViewModel = PersonBaseInfoViewModel()

    var success by mutableStateOf(false)

    fun checkUsernameAvailable() {
        if (matchUsername(username)) {
            usernameAvailable = availableMap[username]
            if (availableMap[username] == null) {
                viewModelScope.launch {
                    val result = PersonRepository.getPersonByUsername(username)
                    usernameAvailable = result.exceptionOrNull() is NotFoundException
                    if (usernameAvailable != null) availableMap[username] = usernameAvailable!!
                }
            }
        }
    }

    fun signUp() {
        viewModelScope.launch {
            val model =
                SignUp(
                    username,
                    password,
                    baseInfoViewModel.name,
                    baseInfoViewModel.sex!!,
                    baseInfoViewModel.major!!.id,
                    baseInfoViewModel.phone.ifEmpty { null },
                    baseInfoViewModel.wechat.ifEmpty { null }
                )
            val res = SecretRepository.signUp(model)
            if (res.isSuccess) {
                success = true
            } else {
                showSnackBar(
                    when (res.exceptionOrNull()) {
                        is BadRequestException -> "Invalid Parameter"
                        is ForbiddenException -> "Username already exists"
                        else -> "Network Error"
                    }
                )
            }
        }
    }
}
