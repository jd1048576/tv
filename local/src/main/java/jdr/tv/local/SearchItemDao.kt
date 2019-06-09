package jdr.tv.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.SearchItem
import jdr.tv.local.entities.Show

@Dao
abstract class SearchItemDao : BaseDao<SearchItem>() {

    @Query("SELECT Show.* FROM Show JOIN SearchItem ON Show.id = SearchItem.showId ORDER BY SearchItem.id ASC")
    abstract fun selectSearchDataSourceFactory(): DataSource.Factory<Int, Show>

    @Query("SELECT * FROM SearchItem WHERE showId = :showId")
    abstract suspend fun selectSearchItem(showId: Long): SearchItem?

    @Query("DELETE FROM SearchItem")
    abstract suspend fun deleteAll()
}
