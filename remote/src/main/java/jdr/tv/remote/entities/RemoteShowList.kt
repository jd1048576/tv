package jdr.tv.remote.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteShowList(
    @Json(name = "page")
    val page: Int,
    @Json(name = "videoList")
    val results: List<RemoteShow>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)
