package jdr.tv.data.local

import androidx.room.Dao
import androidx.room.Query
import java.time.Instant
import jdr.tv.data.local.entities.DetailedShow
import jdr.tv.data.local.entities.Details
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailsDao : BaseDao<Details> {

    @Query("SELECT Show.*, Details.* FROM Show JOIN Details ON Show.id = Details.showId WHERE id = :id ")
    fun selectDetailedShowFlow(id: Long): Flow<DetailedShow>

    @Query("SELECT detailsUpdatedAt FROM Details WHERE showId = :id")
    suspend fun selectUpdatedAt(id: Long): Instant
}
