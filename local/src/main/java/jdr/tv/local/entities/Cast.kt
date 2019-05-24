package jdr.tv.local.entities

data class Cast(
    val character: String,
    val id: Long,
    val name: String,
    val gender: Int,
    val profilePath: String
)
