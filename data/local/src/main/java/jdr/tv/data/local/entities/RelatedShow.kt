package jdr.tv.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    indices = [Index("showId"), Index("relatedShowId")],
    primaryKeys = ["showId", "relatedShowId"],
    foreignKeys = [
        ForeignKey(
            entity = Show::class,
            parentColumns = ["id"],
            childColumns = ["showId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Show::class,
            parentColumns = ["id"],
            childColumns = ["relatedShowId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RelatedShow(
    val showId: Long,
    val relatedShowId: Long,
    val type: Int
) {
    companion object {
        const val TYPE_RECOMMENDED = 1
        const val TYPE_SIMILAR = 2
        fun recommended(showId: Long, relatedShowId: Long) = RelatedShow(
            showId,
            relatedShowId,
            TYPE_RECOMMENDED
        )
        fun similar(showId: Long, relatedShowId: Long) = RelatedShow(
            showId,
            relatedShowId,
            TYPE_SIMILAR
        )
    }
}
