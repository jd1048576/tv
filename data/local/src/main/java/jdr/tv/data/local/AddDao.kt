package jdr.tv.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import jdr.tv.data.local.entities.Add
import jdr.tv.data.local.entities.Show
import kotlinx.coroutines.flow.Flow

@Dao
interface AddDao : BaseDao<Add> {

    @Query("SELECT * FROM Show WHERE EXISTS (SELECT 1 FROM `Add` WHERE showId = id)")
    fun selectAddedShowListFlow(): Flow<List<Show>>

    @Query("SELECT showId FROM `Add`")
    suspend fun selectAddedShowIdList(): List<@JvmSuppressWildcards Long>

    @Transaction
    @Query("SELECT COUNT(*) FROM `Add` WHERE showId = :showId")
    fun selectCount(showId: Long): Flow<Int>

    @Query("DELETE FROM `Add` WHERE showId = :showId")
    suspend fun delete(showId: Long)
}
