package jdr.tv.data.remote.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import jdr.tv.data.remote.entities.RemoteGenre

class RemoteGenreAdapter : JsonAdapter<RemoteGenre>() {

    @FromJson
    override fun fromJson(reader: JsonReader): RemoteGenre {
        var id: Int? = null
        if (reader.peek() == JsonReader.Token.NUMBER) {
            id = reader.nextInt()
        } else {
            reader.beginObject()
            while (reader.hasNext()) {
                if (reader.nextName() == "id") {
                    id = reader.nextInt()
                } else {
                    reader.skipValue()
                }
            }
            reader.endObject()
        }
        return RemoteGenre.createFromId(id)
    }

    override fun toJson(writer: JsonWriter, value: RemoteGenre?) {
        throw IllegalStateException("toJson Not Implemented $value")
    }
}
