package com.gargantua7.cams.gp.android.ui.person

import androidx.lifecycle.viewModelScope
import com.gargantua7.cams.gp.android.logic.exception.AuthorizedException
import com.gargantua7.cams.gp.android.logic.exception.BadRequestException
import com.gargantua7.cams.gp.android.logic.exception.ForbiddenException
import com.gargantua7.cams.gp.android.logic.exception.NotFoundException
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.logic.model.PersonUpdate
import com.gargantua7.cams.gp.android.logic.repository.PersonRepository
import com.gargantua7.cams.gp.android.logic.repository.ResourceRepository
import com.gargantua7.cams.gp.android.ui.component.compose.ExhibitComposeViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException

/**
 * @author Gargantua7
 */
class PersonEditViewModel : ExhibitComposeViewModel<Person, String>() {

    val baseInfoViewModel = PersonBaseInfoViewModel()

    override suspend fun getItem(id: String): Result<Person> {
        return PersonRepository.getPersonByUsername(id)
    }

    fun fresh(person: Person) {
        baseInfoViewModel.let {
            it.wechat = person.wechat ?: ""
            it.phone = person.phone ?: ""
        }
        viewModelScope.launch {
            try {
                val majorRes = ResourceRepository.getMajor(person.majorId)
                val major = majorRes.getOrThrow()
                baseInfoViewModel.major = major
                val collageRes = ResourceRepository.getCollage(major.collageId)
                baseInfoViewModel.collage = collageRes.getOrThrow()
            } catch (_: Throwable) {
                showSnackBar("网络错误")
            }
        }
    }

    fun submit() {
        viewModelScope.launch {
            val res = PersonRepository.personInfoUpdate(
                PersonUpdate(
                    id.value!!,
                    baseInfoViewModel.major!!.id,
                    baseInfoViewModel.phone,
                    baseInfoViewModel.wechat
                )
            )
            showSnackBar(
                if (res.isSuccess) {
                    id.value = id.value
                    "更新成功"
                } else {
                    when (res.exceptionOrNull()) {
                        is BadRequestException, is ForbiddenException -> "Wrong Request Param Format"
                        is AuthorizedException -> "Insufficient Permissions"
                        is NotFoundException -> "Person Not Found"
                        is UnknownHostException -> "Network Error"
                        else -> "Unknown Error"
                    }
                }

            )
        }
    }

}
