package jdr.tv.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.DetailedShow
import jdr.tv.local.entities.Details
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
    abstract fun selectDetailedShowLiveData(id: Long): LiveData<DetailedShow>

    @Query("SELECT lastDetailsUpdate FROM Details WHERE showId = :id")
    abstract suspend fun selectLastDetailsUpdate(id: Long): Instant
}
