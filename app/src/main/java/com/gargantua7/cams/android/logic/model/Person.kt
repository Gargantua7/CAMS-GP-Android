package com.gargantua7.cams.android.logic.model

/**
 * @author Gargantua7
 */
data class Person(
    val username: String,
    val name: String,
    val sex: Boolean,
    val major: String,
    val collage: String,
    val majorId: String,
    val dep: String,
    val depId: Int,
    val permission: Int?,
    val title: String,
    val phone: String?,
    val wechat: String?
)
