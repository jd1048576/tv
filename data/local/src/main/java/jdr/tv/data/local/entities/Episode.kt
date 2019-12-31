package jdr.tv.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.time.Instant

@Entity(
    indices = [Index("id"), Index("seasonId")],
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = Season::class,
            parentColumns = ["id"],
            childColumns = ["seasonId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Episode(
    val airDate: Instant,
    val episodeNumber: Int,
    val id: Long,
    val name: String,
    val overview: String?,
    val seasonId: Long,
    val seasonNumber: Int,
    val stillPath: String?,
    val voteAverage: Double,
    val voteCount: Int
)
