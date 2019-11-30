package jdr.tv.local

import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.Watch

@Dao
interface WatchDao : BaseDao<Watch> {

    @Query("DELETE FROM Watch WHERE episodeId in (:idList)")
    suspend fun deleteAll(idList: List<Long>)
}
