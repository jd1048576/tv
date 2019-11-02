package jdr.tv.local.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cast(
    val character: String,
    val id: Long,
    val name: String,
    val gender: Int,
    val profilePath: String?
)
