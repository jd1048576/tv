package jdr.tv.remote.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Instant

@JsonClass(generateAdapter = true)
class RemoteEpisode(
    @Json(name = "air_date")
    val airDate: Instant,
    @Json(name = "episode_number")
    val episodeNumber: Int,
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "production_code")
    val productionCode: String?,
    @Json(name = "season_number")
    val seasonNumber: Int,
    @Json(name = "show_id")
    val showId: Long,
    @Json(name = "still_path")
    val stillPath: String?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "crew")
    val crew: List<RemoteCrew>,
    @Json(name = "guest_stars")
    val guestStarList: List<RemoteCast>
)