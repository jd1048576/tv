package jdr.tv.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.time.Instant

@Entity(
    indices = [Index("episodeId")],
    primaryKeys = ["episodeId"],
    foreignKeys = [
        ForeignKey(
            entity = Episode::class,
            parentColumns = ["id"],
            childColumns = ["episodeId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Watch(val episodeId: Long, val watchedAt: Instant)
