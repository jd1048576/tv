package jdr.tv.remote.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Instant

@JsonClass(generateAdapter = true)
class RemoteShow(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "first_air_date")
    val firstAirDate: Instant,
    @Json(name = "genre_ids")
    val genreList: List<RemoteGenre>,
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "origin_country")
    val originCountryList: List<String>,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_name")
    val originalName: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)
