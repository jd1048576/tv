package jdr.tv.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class DetailedEpisode(
    @Embedded
    val episode: Episode,
    @Relation(parentColumn = "id", entityColumn = "episodeId", entity = Watch::class)
    private val watchList: List<Watch>
) {
    val watched: Boolean get() = watchList.isNotEmpty()
}
