package jdr.tv.data.local.entities

import java.time.Instant

data class EpisodeItem(
    val episodeNumber: Int,
    val name: String,
    val id: Long,
    val seasonNumber: Int,
    val airDate: Instant,
    val showId: Long,
    val showName: String,
    val posterPath: String
)
