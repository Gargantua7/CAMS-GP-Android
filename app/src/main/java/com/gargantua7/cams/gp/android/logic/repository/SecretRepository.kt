package com.gargantua7.cams.gp.android.logic.repository

import com.gargantua7.cams.gp.android.logic.model.Secret
import com.gargantua7.cams.gp.android.logic.network.NetworkServiceCreator
import com.gargantua7.cams.gp.android.logic.network.SecretService

/**
 * @author Gargantua7
 */
object SecretRepository {

    private val secretService = NetworkServiceCreator.create(SecretService::class.java)

    suspend fun signIn(secret: Secret) = fire { secretService.signIn(secret).get() }


}
