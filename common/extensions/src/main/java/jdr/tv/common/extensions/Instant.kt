package jdr.tv.common.extensions

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss") }

val UTC_ZONE_ID: ZoneId by lazy { ZoneId.of("UTC") }
val SYSTEM_ZONE_ID: ZoneId by lazy { ZoneId.systemDefault() }
val INSTANT_ZERO: Instant = Instant.ofEpochSecond(0L)

fun Instant.toFormattedString(dateTimeFormatter: DateTimeFormatter = formatter): String {
    return if (epochSecond == 0L) "Date Unavailable" else atZone(SYSTEM_ZONE_ID).format(dateTimeFormatter)
}

fun Instant?.olderThan(days: Long = 0, hours: Long = 0, minutes: Long = 0, seconds: Long = 0): Boolean {
    val d = Duration.ofDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds)
    return if (this == null) return true else (Duration.between(this, Instant.now()).seconds > d.seconds)
}
