package jdr.tv.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jdr.tv.local.converters.InstantTypeConverter
import jdr.tv.local.converters.ListTypeConverter
import jdr.tv.local.entities.Details
import jdr.tv.local.entities.DiscoverItem
import jdr.tv.local.entities.Episode
import jdr.tv.local.entities.RelatedShow
import jdr.tv.local.entities.Season
import jdr.tv.local.entities.Show

@Database(
    entities = [Show::class, Details::class, Season::class, Episode::class, DiscoverItem::class, RelatedShow::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(InstantTypeConverter::class, ListTypeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun showDao(): ShowDao

    abstract fun detailsDao(): DetailsDao

    abstract fun seasonDao(): SeasonDao

    abstract fun episodeDao(): EpisodeDao

    abstract fun discoverItemDao(): DiscoverItemDao

    abstract fun relatedShowDao(): RelatedShowDao
}
