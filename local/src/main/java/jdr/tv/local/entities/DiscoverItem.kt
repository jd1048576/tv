package jdr.tv.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index

@Entity(
    indices = [Index("id"), Index("showId")],
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = Show::class,
            parentColumns = ["id"],
            childColumns = ["showId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DiscoverItem(
    val id: Long,
    val showId: Long,
    val type: Int
) {
    @Ignore
    constructor(page: Int, position: Int, showId: Long, type: Int) :
        this(String.format("%d%02d", ((page - 1) * 20) + position, type).toLong(), showId, type)

    companion object {
        const val TYPE_TRENDING = 1
        const val TYPE_SEARCH = 2
    }
}
