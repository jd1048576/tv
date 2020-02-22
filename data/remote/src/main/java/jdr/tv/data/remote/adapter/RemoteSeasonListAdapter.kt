package jdr.tv.data.remote.adapter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.time.Instant
import jdr.tv.common.extensions.INSTANT_ZERO
import jdr.tv.data.remote.entities.RemoteEpisode
import jdr.tv.data.remote.entities.RemoteSeason

class RemoteSeasonListAdapter(private val moshi: Moshi) : JsonAdapter<List<RemoteSeason>>() {

    companion object {
        private val seasonType = Types.newParameterizedType(List::class.java, TransientSeason::class.java)
        private val seasonRegex = Regex("^season/[1-9][0-9]*")
    }

    private val seasonListAdapter by lazy { moshi.adapter<List<TransientSeason>>(
        seasonType
    ) }

    private val episodeListAdapter by lazy { moshi.adapter(TransientEpisodeList::class.java) }

    override fun fromJson(reader: JsonReader): List<RemoteSeason> {
        var showId: Long? = null
        val seasonMap = HashMap<Int, TransientSeason>()
        val episodeList = ArrayList<TransientEpisodeList>()
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            when {
                name == "id" -> showId = reader.nextLong()
                name == "seasons" -> seasonListAdapter.fromJson(reader)?.associateByTo(seasonMap, TransientSeason::seasonNumber)
                name.matches(seasonRegex) -> {
                    episodeListAdapter.fromJson(reader)?.takeIf { it.seasonNumber != 0 }?.also { episodeList.add(it) }
                }
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        checkNotNull(showId) { "RemoteShow Id Cannot be Null" }
        return episodeList.map { seasonMap.getValue(it.seasonNumber).toRemoteSeason(showId, it) }
    }

    override fun toJson(writer: JsonWriter, value: List<RemoteSeason>?) {
        throw IllegalStateException("toJson Not Implemented $value")
    }

    @JsonClass(generateAdapter = true)
    internal class TransientSeason(
        @Json(name = "air_date")
        val airDate: Instant = INSTANT_ZERO,
        @Json(name = "episode_count")
        val episodeCount: Int,
        @Json(name = "id")
        val id: Long,
        @Json(name = "name")
        val name: String,
        @Json(name = "overview")
        val overview: String?,
        @Json(name = "poster_path")
        val posterPath: String?,
        @Json(name = "season_number")
        val seasonNumber: Int
    ) {
        fun toRemoteSeason(showId: Long, episodeList: TransientEpisodeList): RemoteSeason {
            return RemoteSeason(
                airDate,
                id,
                episodeList.episodeList,
                name,
                overview,
                posterPath,
                showId,
                seasonNumber
            )
        }
    }

    @JsonClass(generateAdapter = true)
    internal class TransientEpisodeList(
        @Json(name = "season_number")
        val seasonNumber: Int,
        @Json(name = "episodes")
        val episodeList: List<RemoteEpisode>
    )
}
