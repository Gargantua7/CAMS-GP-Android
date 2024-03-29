package com.gargantua7.cams.gp.android.ui.secret

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.model.Secret
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import com.gargantua7.cams.gp.android.logic.repository.SecretRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
class SignInViewModel : ComposeViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisibility by mutableStateOf(false)

    var success by mutableStateOf(false)

    fun login() {
        Log.d("Event", "Login At ${LocalDateTime.now()}")
        viewModelScope.launch {
            loading = true
            val result = SecretRepository.signIn(Secret(username, password))
            loading = false
            if (result.isSuccess) {
                val session = result.getOrNull()?.get("session") ?: ""
                CAMSApplication.username = username
                CAMSApplication.session.value = session
                CAMSApplication.msgRefresh = true
                SecretRepository.saveSession()
                PersonRepository.saveUsername()
                success = true
            } else {
                showSnackBar(
                    when (result.exceptionOrNull()) {
                        is AuthorizedException -> "用户名或密码错误"
                        is UnknownHostException -> "网络错误"
                        else -> "未知错误"
                    }
                )
            }
        }
    }
}
