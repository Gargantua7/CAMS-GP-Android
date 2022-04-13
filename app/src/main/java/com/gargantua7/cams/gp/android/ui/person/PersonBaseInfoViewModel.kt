package com.gargantua7.cams.gp.android.ui.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gargantua7.cams.gp.android.logic.model.Collage
import com.gargantua7.cams.gp.android.logic.model.Major
import com.gargantua7.cams.gp.android.logic.repository.ResourceRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeViewModel
import kotlinx.coroutines.launch

/**
 * @author Gargantua7
 */
class PersonBaseInfoViewModel : ViewModel() {

    var name by mutableStateOf("")

    var sex by mutableStateOf<Boolean?>(null)

    var collage by mutableStateOf<Collage?>(null)

    var major by mutableStateOf<Major?>(null)

    var majorMap = mutableStateMapOf<Collage, Set<Major>?>()

    var phone by mutableStateOf("")

    var wechat by mutableStateOf("")

    var success by mutableStateOf(false)

    fun getCollageList(parent: ComposeViewModel) {
        if (majorMap.isNotEmpty()) return
        viewModelScope.launch {
            val res = ResourceRepository.getCollageList()
            if (res.isSuccess) {
                res.getOrThrow().forEach {
                    majorMap.putIfAbsent(it, null)
                }
            } else parent.showSnackBar("Network Error")
        }
    }

    fun getMajorList(parent: ComposeViewModel) {
        if (!majorMap[collage].isNullOrEmpty()) return
        collage?.let {
            viewModelScope.launch {
                val res = ResourceRepository.getCollageMajorList(it.id)
                if (res.isSuccess) {
                    majorMap[it] = res.getOrThrow().toSet()
                } else parent.showSnackBar("Network Error")
            }
        }
    }
}
