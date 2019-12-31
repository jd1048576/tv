package jdr.tv.data.local

import androidx.room.Dao
import androidx.room.Query
import jdr.tv.data.local.entities.Watch

@Dao
interface WatchDao : BaseDao<Watch> {

    @Query("DELETE FROM Watch WHERE episodeId in (:idList)")
    suspend fun deleteAll(idList: List<Long>)
}
