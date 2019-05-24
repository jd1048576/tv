package jdr.tv.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import jdr.tv.local.entities.DetailedSeason
import jdr.tv.local.entities.Season

@Dao
abstract class SeasonDao : BaseDao<Season>() {

    @Transaction
    @Query("SELECT * FROM Season WHERE Season.showId = :showId ORDER BY seasonNumber ASC")
    abstract fun selectListLiveData(showId: Long): LiveData<List<DetailedSeason>>

    @Query("SELECT COUNT(*) FROM Season WHERE Season.showId = :showId")
    abstract suspend fun selectSeasonCount(showId: Long): Int
}
