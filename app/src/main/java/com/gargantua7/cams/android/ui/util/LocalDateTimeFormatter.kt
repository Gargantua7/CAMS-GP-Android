package com.gargantua7.cams.android.ui.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Gargantua7
 */
fun LocalDateTime.format(): String = format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss"))

fun String.toLocalDateTime(): LocalDateTime = LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
