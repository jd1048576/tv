package jdr.tv.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import jdr.tv.data.local.entities.DetailedSeason
import jdr.tv.data.local.entities.Season
import kotlinx.coroutines.flow.Flow

@Dao
interface SeasonDao : BaseDao<Season> {

    @Transaction
    @Query("SELECT Season.* FROM Season WHERE Season.showId = :showId ORDER BY seasonNumber ASC")
    fun selectDetailedSeasonListFlow(showId: Long): Flow<List<DetailedSeason>>

    @Query("SELECT COUNT(*) FROM Season WHERE Season.showId = :showId")
    suspend fun selectSeasonCount(showId: Long): Int
}
