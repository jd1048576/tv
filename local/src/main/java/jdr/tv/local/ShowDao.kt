package jdr.tv.local

import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.Show
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDao : BaseDao<Show> {

    @Query("SELECT Show.* FROM Show WHERE id = :id")
    fun select(id: Long): Show?

    @Query("SELECT Show.* FROM Show WHERE id = :id")
    fun selectFlow(id: Long): Flow<Show>

/*    @Query("SELECT * FROM Show WHERE added = 1 ORDER BY name ASC")
    fun selectAddedShowListDataSourceFactory(): DataSource.Factory<Int, Show>

    @Query("UPDATE Show SET added = :added WHERE id = :id")
    fun updateAdded(id: Long, added: Boolean)*/

    /* @Query("DELETE FROM Show WHERE added = 0 AND lastUpdate < (strftime('%s','now') - (3 * 86400))")
     suspend fun invalidate()*/

    @Query("DELETE FROM Show")
    suspend fun deleteAll()
}
