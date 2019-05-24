package jdr.tv.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.DiscoverItem
import jdr.tv.local.entities.Show

@Dao
abstract class DiscoverItemDao : BaseDao<DiscoverItem>() {

    @Query("SELECT Show.* FROM Show JOIN DiscoverItem ON Show.id = DiscoverItem.showId WHERE type = :type ORDER BY DiscoverItem.id ASC")
    abstract fun selectDiscoverListDataSourceFactory(type: Int): DataSource.Factory<Int, Show>

    @Query("SELECT COUNT(*) FROM DiscoverItem WHERE type = :type")
    abstract fun selectDiscoverItemCount(type: Int): Int

    @Query("SELECT COUNT(*) FROM Show JOIN DiscoverItem ON Show.id = DiscoverItem.showId WHERE type = :type AND lastUpdate < strftime('%s','now') - 28800")
    abstract fun selectDiscoverItemShowCount(type: Int): Int

    @Query("DELETE FROM DiscoverItem WHERE type = :type")
    abstract suspend fun deleteAllWithType(type: Int)
}
