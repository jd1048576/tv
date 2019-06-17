package jdr.tv.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
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
data class SearchItem(
    override val id: Long,
    override val showId: Long,
    override val page: Int
) : PaginatedItem