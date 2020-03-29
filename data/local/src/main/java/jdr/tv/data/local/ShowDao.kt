package jdr.tv.data.local

import androidx.room.Dao
import androidx.room.Query
import jdr.tv.data.local.entities.Show
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface ShowDao : BaseDao<Show> {

    @Query("SELECT Show.* FROM Show WHERE id = :id")
    fun select(id: Long): Show?

    @Query("SELECT Show.* FROM Show WHERE id = :id")
    fun selectFlow(id: Long): Flow<Show>

    @Query("DELETE FROM Show WHERE NOT EXISTS (SELECT 1 FROM `Add` WHERE showId = Show.id) AND Show.updatedAt < :olderThan")
     suspend fun deleteAll(olderThan: Instant)

    @Query("DELETE FROM Show")
    suspend fun deleteAll()
}
