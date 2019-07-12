package jdr.tv.local

import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.Season

@Dao
abstract class SeasonDao : BaseDao<Season>() {

    @Query(
        """
        SELECT COUNT(*) FROM Season 
        WHERE Season.showId = :showId
        """
    )
    abstract suspend fun selectSeasonCount(showId: Long): Int
}
