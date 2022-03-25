package com.gargantua7.cams.gp.android.logic.repository

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.dao.SecretDao
import com.gargantua7.cams.gp.android.logic.model.Secret
import com.gargantua7.cams.gp.android.logic.network.NetworkServiceCreator
import com.gargantua7.cams.gp.android.logic.network.SecretService

/**
 * @author Gargantua7
 */
object SecretRepository {

    private val secretService = NetworkServiceCreator.create(SecretService::class.java)

    suspend fun signIn(secret: Secret) = fire { secretService.signIn(secret).get() }

    suspend fun signOut() = fire { secretService.signOut().get() }

    suspend fun saveSession() {
        CAMSApplication.session.value?.let {
            SecretDao.saveSession(it)
        }
    }

    suspend fun loadSession() {
        SecretDao.loadSession()?.let {
            CAMSApplication.session.value = it
        }
    }

    suspend fun removeSession() = SecretDao.removeSession()

}
