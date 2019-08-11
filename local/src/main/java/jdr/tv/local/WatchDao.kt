package jdr.tv.local

import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.Watch

@Dao
abstract class WatchDao : BaseDao<Watch>() {

    @Query("DELETE FROM Watch WHERE episodeId in(:idList)")
    abstract suspend fun deleteAll(idList: List<Long>)
}
