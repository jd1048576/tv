package jdr.tv.local.converters

import androidx.room.TypeConverter

import java.time.Instant

object InstantTypeConverter {

    @TypeConverter
    @JvmStatic
    fun toInstant(epochSecond: Long?): Instant {
        return Instant.ofEpochSecond(epochSecond ?: 0L)
    }

    @TypeConverter
    @JvmStatic
    fun fromInstant(instant: Instant?): Long {
        return instant?.epochSecond ?: 0L
    }
}
