package com.gargantua7.cams.gp.android.ui.secret

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.gargantua7.cams.gp.android.logic.exception.NotFoundException
import com.gargantua7.cams.gp.android.logic.model.Collage
import com.gargantua7.cams.gp.android.logic.model.Major
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import com.gargantua7.cams.gp.android.logic.repository.ResourceRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
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

    var name by mutableStateOf("")

    var sex by mutableStateOf<Boolean?>(null)

    var collage by mutableStateOf<Collage?>(null)

    var major by mutableStateOf<Major?>(null)

    var majorMap = mutableStateMapOf<Collage, Set<Major>?>()

    var phone by mutableStateOf("")

    var wechat by mutableStateOf("")

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

    fun getCollageList() {
        if (majorMap.isNotEmpty()) return
        viewModelScope.launch {
            val res = ResourceRepository.getCollageList()
            if (res.isSuccess) {
                res.getOrThrow().forEach {
                    majorMap.putIfAbsent(it, null)
                }
            } else showSnackBar("Network Error")
        }
    }

    fun getMajorList() {
        if (!majorMap[collage].isNullOrEmpty()) return
        collage?.let {
            viewModelScope.launch {
                val res = ResourceRepository.getCollageMajorList(it.id)
                if (res.isSuccess) {
                    majorMap[it] = res.getOrThrow().toSet()
                } else showSnackBar("Network Error")
            }
        }
    }
}
