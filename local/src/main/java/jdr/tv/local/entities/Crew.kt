package jdr.tv.local.entities

data class Crew(
    val department: String,
    val id: Long,
    val name: String,
    val gender: Int,
    val job: String,
    val profilePath: String
)
