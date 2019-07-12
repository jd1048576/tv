package jdr.tv.base.extensions

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import java.time.Instant
import java.time.format.DateTimeParseException

val MOSHI_DEFAULT: Moshi = Moshi.Builder().addAdapter(InstantAdapter()).build()

inline fun <reified T> Moshi.Builder.addAdapter(jsonAdapter: JsonAdapter<T>): Moshi.Builder = apply {
    add(T::class.java, jsonAdapter)
}

inline fun <reified T> Moshi.Builder.addListAdapter(jsonAdapter: JsonAdapter<List<T>>): Moshi.Builder = apply {
    add(createListType<T>(), jsonAdapter)
}

inline fun <reified T> Moshi.adapter(): JsonAdapter<T> {
    return adapter(T::class.java)
}

inline fun <reified T> Moshi.listAdapter(): JsonAdapter<List<T>> {
    return adapter(createListType<T>())
}

inline fun <reified T> createListType(): Type {
    return Types.newParameterizedType(List::class.java, T::class.java)
}

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
