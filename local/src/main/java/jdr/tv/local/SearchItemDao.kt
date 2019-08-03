package jdr.tv.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.SearchItem
import jdr.tv.local.entities.Show

@Dao
abstract class SearchItemDao : BaseDao<SearchItem>() {

    @Query(
        """
        SELECT Show.* FROM Show 
        JOIN SearchItem ON Show.id = SearchItem.showId 
        ORDER BY SearchItem.id ASC
        """
    )
    abstract fun selectSearchShowList(): LiveData<List<Show>>

    @Query("DELETE FROM SearchItem")
    abstract suspend fun deleteAll()
}
