package jdr.tv.data.remote.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteExternalIds(
    @Json(name = "facebook_id")
    val facebookId: String?,
    @Json(name = "freebase_id")
    val freebaseId: String?,
    @Json(name = "freebase_mid")
    val freebaseMid: String?,
    @Json(name = "imdb_id")
    val imdbId: String?,
    @Json(name = "instagram_id")
    val instagramId: String?,
    @Json(name = "tvdb_id")
    val tvdbId: Int?,
    @Json(name = "tvrage_id")
    val tvrageId: Int?,
    @Json(name = "twitter_id")
    val twitterId: String?
)
