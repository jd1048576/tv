package jdr.tv.local

import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.DetailedShow
import jdr.tv.local.entities.Details
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
abstract class DetailsDao : BaseDao<Details>() {

    @Query(
        """
        SELECT * FROM Show 
        JOIN Details ON Show.id = Details.showId 
        WHERE id = :id
        """
    )
    abstract fun selectDetailedShow(id: Long): DetailedShow?

    @Query(
        """
        SELECT * FROM Show 
        JOIN Details ON Show.id = Details.showId 
        WHERE id = :id
        """
    )
    abstract fun selectDetailedShowFlow(id: Long): Flow<DetailedShow>

    @Query("SELECT lastDetailsUpdate FROM Details WHERE showId = :id")
    abstract suspend fun selectLastDetailsUpdate(id: Long): Instant
}
