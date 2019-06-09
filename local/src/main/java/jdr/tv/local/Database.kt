package jdr.tv.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jdr.tv.local.converters.InstantTypeConverter
import jdr.tv.local.converters.ListTypeConverter
import jdr.tv.local.entities.SearchItem
import jdr.tv.local.entities.Show

@Database(
    entities = [Show::class, SearchItem::class],
    exportSchema = false,
    version = 2
)
@TypeConverters(InstantTypeConverter::class, ListTypeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun showDao(): ShowDao

    abstract fun searchItemDao(): SearchItemDao
}
