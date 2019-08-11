package jdr.tv.remote.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import jdr.tv.base.extensions.MOSHI_DEFAULT
import jdr.tv.base.extensions.adapter
import jdr.tv.base.extensions.listAdapter
import jdr.tv.remote.entities.RemoteSeason

internal class RemoteSeasonListAdapter : JsonAdapter<List<RemoteSeason>>() {

    companion object {
        val seasonRegex = Regex("^season/[1-9][0-9]*")
    }

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
                name.matches(seasonRegex) -> {
                    episodeListAdapter.fromJson(reader)?.takeIf { it.seasonNumber != 0 }?.also { map[it.seasonNumber] = it }
                }
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        checkNotNull(showId) { "RemoteShow Id Cannot be Null" }
        return seasonList?.filter { it.seasonNumber != 0 }?.map { it.toRemoteSeason(showId, map[it.seasonNumber]) }.orEmpty()
    }

    override fun toJson(writer: JsonWriter, value: List<RemoteSeason>?) {
        throw IllegalStateException("toJson Not Implemented $value")
    }
}
