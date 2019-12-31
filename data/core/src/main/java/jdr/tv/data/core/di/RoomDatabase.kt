package jdr.tv.data.core.di

import androidx.room.TypeConverters
import androidx.room.withTransaction
import jdr.tv.data.local.Database
import jdr.tv.data.local.converters.InstantTypeConverter
import jdr.tv.data.local.converters.ListTypeConverter
import jdr.tv.data.local.entities.Add
import jdr.tv.data.local.entities.Details
import jdr.tv.data.local.entities.Episode
import jdr.tv.data.local.entities.RelatedShow
import jdr.tv.data.local.entities.SearchItem
import jdr.tv.data.local.entities.Season
import jdr.tv.data.local.entities.Show
import jdr.tv.data.local.entities.Watch

@androidx.room.Database(
    entities = [Add::class, Details::class, Episode::class, RelatedShow::class, Show::class, SearchItem::class, Season::class, Watch::class],
    version = 1
)
@TypeConverters(InstantTypeConverter::class, ListTypeConverter::class)
abstract class RoomDatabase : androidx.room.RoomDatabase(), Database {
    override suspend fun <R> withTransaction(block: suspend () -> R): R = (this as androidx.room.RoomDatabase).withTransaction(block)
}
