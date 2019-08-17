package jdr.tv.remote.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import jdr.tv.base.extensions.INSTANT_ZERO
import java.time.Instant

class RemoteSeason(
    val airDate: Instant,
    val id: Long,
    val episodesList: List<RemoteEpisode>,
    val name: String,
    val overview: String?,
    val posterPath: String?,
    val showId: Long,
    val seasonNumber: Int
) {
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
        fun toRemoteSeason(showId: Long, episodeList: TransientEpisodeList?): RemoteSeason {
            return RemoteSeason(airDate, id, episodeList?.episodeList.orEmpty(), name, overview, posterPath, showId, seasonNumber)
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
