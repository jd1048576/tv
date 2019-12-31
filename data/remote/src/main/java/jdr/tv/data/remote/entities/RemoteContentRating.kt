package jdr.tv.data.remote.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteContentRating(
    @Json(name = "iso_3166_1")
    val iso31661: String,
    @Json(name = "rating")
    val rating: String
)
