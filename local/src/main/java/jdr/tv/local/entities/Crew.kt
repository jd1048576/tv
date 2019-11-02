package jdr.tv.local.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Crew(
    val department: String,
    val id: Long,
    val name: String,
    val gender: Int,
    val job: String,
    val profilePath: String?
)
