package jdr.tv.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class DetailedSeason(
    @Embedded
    val season: Season,
    @Relation(parentColumn = "id", entityColumn = "seasonId", entity = Episode::class)
    val episodeList: List<DetailedEpisode>
) {
    val watchCount: Int get() = episodeList.count { it.watched }
}
