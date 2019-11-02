package jdr.tv.local.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Network(
    val id: Long,
    val logoPath: String?,
    val name: String,
    val originCountry: String
)
