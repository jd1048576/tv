package jdr.tv.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.time.Instant

@Entity(
    indices = [Index("id"), Index("showId")],
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = Show::class,
            parentColumns = ["id"],
            childColumns = ["showId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Season(
    val airDate: Instant,
    val id: Long,
    val name: String,
    val overview: String?,
    val posterPath: String?,
    val showId: Long,
    val seasonNumber: Int
)
