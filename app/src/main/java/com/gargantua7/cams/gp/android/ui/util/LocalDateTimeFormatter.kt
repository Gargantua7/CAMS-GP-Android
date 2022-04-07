package com.gargantua7.cams.gp.android.ui.util

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Gargantua7
 */
fun LocalDateTime.format(): String = format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss"))

fun LocalDateTime.networkFormat(): String = format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse("$this +08", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss X"))

fun LocalDateTime.toIntuitive(): String {
    return when (val duration = Duration.between(this, LocalDateTime.now()).toMinutes()) {
        0L -> "Just Now"
        in 1L..59L -> "$duration Minutes Ago"
        in 60L..1439L -> (duration / 60).toString() + " Hours Ago"
        in 1440L..10079L -> (duration / 1440).toString() + " Days Ago"
        else -> format()
    }
}
