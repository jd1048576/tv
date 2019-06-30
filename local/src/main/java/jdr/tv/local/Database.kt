package jdr.tv.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jdr.tv.local.converters.InstantTypeConverter
import jdr.tv.local.converters.ListTypeConverter
import jdr.tv.local.entities.Details
import jdr.tv.local.entities.Episode
import jdr.tv.local.entities.RelatedShow
import jdr.tv.local.entities.SearchItem
import jdr.tv.local.entities.Season
import jdr.tv.local.entities.Show

@Database(
    entities = [Details::class, Episode::class, RelatedShow::class, Show::class, SearchItem::class, Season::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(InstantTypeConverter::class, ListTypeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun detailsDao(): DetailsDao

    abstract fun episodeDao(): EpisodeDao

    abstract fun relatedShowDao(): RelatedShowDao

    abstract fun showDao(): ShowDao

    abstract fun searchItemDao(): SearchItemDao

    abstract fun seasonDao(): SeasonDao
}
