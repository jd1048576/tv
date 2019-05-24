package jdr.tv.local.entities

import com.squareup.moshi.Json

data class ExternalIds(
    @Json(name = "imdb_id")
    val imdbId: String,
    @Json(name = "facebook_id")
    val facebookId: String,
    @Json(name = "instagram_id")
    val instagramId: String,
    @Json(name = "twitter_id")
    val twitterId: String
)
