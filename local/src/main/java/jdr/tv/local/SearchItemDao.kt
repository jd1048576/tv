package jdr.tv.local

import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.SearchItem
import jdr.tv.local.entities.Show
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchItemDao : BaseDao<SearchItem> {

    @Query("SELECT Show.* FROM Show JOIN SearchItem ON Show.id = SearchItem.showId ORDER BY SearchItem.id ASC")
    fun selectSearchShowList(): List<Show>

    @Query("SELECT Show.* FROM Show JOIN SearchItem ON Show.id = SearchItem.showId ORDER BY SearchItem.id ASC")
    fun selectSearchShowListFlow(): Flow<List<Show>>

    @Query("DELETE FROM SearchItem")
    suspend fun deleteAll()
}
