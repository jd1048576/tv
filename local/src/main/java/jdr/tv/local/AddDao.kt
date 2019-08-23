package jdr.tv.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import jdr.tv.local.entities.Add
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AddDao : BaseDao<Add>() {

    @Transaction
    @Query("SELECT COUNT(*) FROM `Add` WHERE showId = :showId")
    abstract fun selectAdded(showId: Long): Flow<Int>

    @Query("DELETE FROM `Add` WHERE showId = :showId")
    abstract suspend fun delete(showId: Long)
}
