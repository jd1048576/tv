package jdr.tv.local.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreatedBy(
    val id: Long,
    val name: String,
    val gender: Int,
    val profilePath: String?
)
