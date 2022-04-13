package com.gargantua7.cams.gp.android.logic.model

/**
 * @author Gargantua7
 */
data class SignUp(
    val username: String,
    val password: String,
    val name: String,
    val sex: Boolean,
    val majorId: String,
    val phone: String?,
    val wechat: String?
)
