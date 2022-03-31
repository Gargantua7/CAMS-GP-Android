package com.gargantua7.cams.gp.android.logic.repository

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.dao.PersonDao
import com.gargantua7.cams.gp.android.logic.model.PersonSearcher
import com.gargantua7.cams.gp.android.logic.network.NetworkServiceCreator
import com.gargantua7.cams.gp.android.logic.network.PersonService

/**
 * @author Gargantua7
 */
object PersonRepository {

    private val personService = NetworkServiceCreator.create(PersonService::class.java)

    suspend fun getMyInfo() = fire {
        personService.getMe().get().apply {
            if (isSuccess) {
                saveMe()
            }
        }
    }

    suspend fun getPersonByUsername(username: String) = fire {
        personService.getPersonByUsername(username).get()
    }

    suspend fun searchPerson(page: Int, searcher: PersonSearcher) = fire {
        personService.searchPerson(
            page,
            username = searcher.username,
            name = searcher.name,
            sex = searcher.sex,
            depId = searcher.depId,
            permissionLevel = searcher.permissionLevel
        ).get()
    }

    suspend fun saveUsername() {
        CAMSApplication.username?.let {
            PersonDao.saveUsername(it)
        }
    }

    suspend fun loadUsername() {
        PersonDao.loadUsername()?.let {
            CAMSApplication.username = it
        }
    }

    suspend fun removeUsername() = PersonDao.removeUsername()

    suspend fun saveMe() {
        /** TODO **/
    }

}
