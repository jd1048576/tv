package jdr.tv.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import java.time.Instant

data class DetailedSeason(
    @Embedded
    val season: Season,
    @Relation(parentColumn = "id", entityColumn = "seasonId")
    val episodeList: List<Episode>
) {

    val watchedEpisodeCount: Int
        get() = episodeList.count { it.watchedAt.epochSecond != 0L }

    val watched: Boolean
        get() = episodeList.size == watchedEpisodeCount

    val airedEpisodeList: List<Episode>
        get() = Instant.now().let { instant -> episodeList.filter { it.airDate.isBefore(instant) } }

    val upToDate: Boolean
        get() = airedEpisodeList.size == watchedEpisodeCount
}