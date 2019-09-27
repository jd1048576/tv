package jdr.tv.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import jdr.tv.local.entities.Add
import jdr.tv.local.entities.Show
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AddDao : BaseDao<Add>() {

    @Query("SELECT * FROM Show WHERE EXISTS (SELECT 1 FROM `Add` WHERE showId = id)")
    abstract fun selectAddedShowListFlow(): Flow<List<Show>>

    @Query("SELECT showId FROM `Add`")
    abstract suspend fun selectAddedShowIdList(): List<Long>

    @Transaction
    @Query("SELECT COUNT(*) FROM `Add` WHERE showId = :showId")
    abstract fun selectCount(showId: Long): Flow<Int>

    @Query("DELETE FROM `Add` WHERE showId = :showId")
    abstract suspend fun delete(showId: Long)
}
