package jdr.tv.remote.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import jdr.tv.base.extensions.INSTANT_ZERO
import java.time.Instant
import java.time.format.DateTimeParseException

internal class InstantAdapter : JsonAdapter<Instant>() {

    override fun fromJson(reader: JsonReader): Instant {
        return if (reader.peek() == JsonReader.Token.NULL) {
            reader.skipValue()
            INSTANT_ZERO
        } else {
            try {
                Instant.parse(reader.nextString() + "T00:00:00Z")
            } catch (e: DateTimeParseException) {
                INSTANT_ZERO
            }
        }
    }

    override fun toJson(writer: JsonWriter, value: Instant?) {
        throw IllegalStateException("toJson Not Implemented $value")
    }
}
