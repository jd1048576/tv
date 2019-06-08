package jdr.tv.remote.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import jdr.tv.base.extensions.MOSHI_DEFAULT
import jdr.tv.base.extensions.adapter
import jdr.tv.base.extensions.listAdapter
import jdr.tv.remote.entities.RemoteSeason

internal class RemoteSeasonListAdapter : JsonAdapter<List<RemoteSeason>>() {

    private val seasonListAdapter by lazy { MOSHI_DEFAULT.listAdapter<RemoteSeason.TransientSeason>() }

    private val episodeListAdapter by lazy { MOSHI_DEFAULT.adapter<RemoteSeason.TransientEpisodeList>() }

    override fun fromJson(reader: JsonReader): List<RemoteSeason> {
        var showId: Long? = null
        var seasonList: List<RemoteSeason.TransientSeason>? = null
        val map = HashMap<Int, RemoteSeason.TransientEpisodeList>()
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            when {
                name == "id" -> showId = reader.nextLong()
                name == "seasons" -> seasonList = seasonListAdapter.fromJson(reader)!!
                name.contains("season/") -> {
                    episodeListAdapter.fromJson(reader)?.takeIf { it.seasonNumber != 0 }?.also { map[it.seasonNumber] = it }
                }
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        checkNotNull(showId) { "RemoteShow Id Cannot be Null" }
        checkNotNull(seasonList) { "RemoteSeason List Cannot be Null" }
        return seasonList.map { it.toRemoteSeason(showId, map[it.seasonNumber]) }.toList()
    }

    override fun toJson(writer: JsonWriter, value: List<RemoteSeason>?) {
        throw IllegalStateException("toJson Not Implemented $value")
    }
}
