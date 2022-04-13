package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.CAMSApplication
import com.gargantua7.cams.gp.android.logic.model.NetworkResponse
import com.gargantua7.cams.gp.android.logic.model.NoResultResponse
import com.gargantua7.cams.gp.android.logic.model.Secret
import com.gargantua7.cams.gp.android.logic.model.SignUp
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @author Gargantua7
 */
interface SecretService {

    @POST("secret/sign/in")
    suspend fun signIn(@Body secret: Secret): NetworkResponse<Map<String, String>>

    @POST("secret/sign/up")
    suspend fun signUp(@Body signUp: SignUp): NoResultResponse

    @POST("secret/sign/out")
    suspend fun signOut(@Header("session") session: String? = CAMSApplication.session.value): NoResultResponse

}
