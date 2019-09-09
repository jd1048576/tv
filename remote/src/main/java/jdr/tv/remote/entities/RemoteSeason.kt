package jdr.tv.remote.entities

import java.time.Instant

class RemoteSeason(
    val airDate: Instant,
    val id: Long,
    val episodesList: List<RemoteEpisode>,
    val name: String,
    val overview: String?,
    val posterPath: String?,
    val showId: Long,
    val seasonNumber: Int
)
