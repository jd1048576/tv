package jdr.tv.local.entities

data class Video(
    val countryCode: String,
    val key: String,
    val languageCode: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)
