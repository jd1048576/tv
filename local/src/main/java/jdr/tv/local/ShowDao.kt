package jdr.tv.local

import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.Show
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ShowDao : BaseDao<Show>() {

    @Query("SELECT Show.* FROM Show WHERE id = :id")
    abstract fun select(id: Long): Show?

    @Query("SELECT Show.* FROM Show WHERE id = :id")
    abstract fun selectFlow(id: Long): Flow<Show>

    @Query("SELECT id FROM Show WHERE EXISTS (SELECT 1 FROM `Add` WHERE showId = id) AND updatedAt < :updatedAt")
    abstract suspend fun selectShowSyncIdList(updatedAt: Long): List<Long>

/*    @Query("SELECT * FROM Show WHERE added = 1 ORDER BY name ASC")
    abstract fun selectAddedShowListDataSourceFactory(): DataSource.Factory<Int, Show>

    @Query("UPDATE Show SET added = :added WHERE id = :id")
    abstract fun updateAdded(id: Long, added: Boolean)*/

    /* @Query("DELETE FROM Show WHERE added = 0 AND lastUpdate < (strftime('%s','now') - (3 * 86400))")
     abstract suspend fun invalidate()*/

    @Query("DELETE FROM Show")
    abstract suspend fun deleteAll()
}
