package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.NetworkResponse
import com.gargantua7.cams.gp.android.logic.model.NoResultResponse
import com.gargantua7.cams.gp.android.logic.model.Secret
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @author Gargantua7
 */
interface SecretService {

    @POST("secret/sign/in")
    suspend fun signIn(@Body secret: Secret): NetworkResponse<Map<String, String>>

    @POST("secret/sign/out")
    suspend fun signOut(@Header("session") session: String? = CAMSApplication.session.value): NoResultResponse

}
