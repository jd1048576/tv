package jdr.tv.data.remote.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteCreatedBy(
    @Json(name = "credit_id")
    val creditId: String,
    @Json(name = "gender")
    val gender: Int = 0,
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "profile_path")
    val profilePath: String?
)
