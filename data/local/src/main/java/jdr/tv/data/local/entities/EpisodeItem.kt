package jdr.tv.data.local.entities

data class EpisodeItem(
    val episodeNumber: Int,
    val name: String,
    val id: Long,
    val seasonNumber: Int,
    val showId: Long,
    val showName: String,
    val posterPath: String
)
