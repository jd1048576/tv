package jdr.tv.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.Genre
import jdr.tv.local.entities.Show
import java.time.Instant

@Dao
abstract class ShowDao : BaseDao<Show>() {

    @Query("SELECT * FROM Show WHERE id = :id")
    abstract fun selectLiveData(id: Long): LiveData<Show>

/*    @Query("SELECT * FROM Show WHERE added = 1 ORDER BY name ASC")
    abstract fun selectAddedShowListDataSourceFactory(): DataSource.Factory<Int, Show>

    @Query("UPDATE Show SET added = :added WHERE id = :id")
    abstract fun updateAdded(id: Long, added: Boolean)*/

    @Query("UPDATE Show SET backdropPath = :backdropPath, firstAirDate = :firstAirDate, genreList = :genreList, name = :name, originCountry = :originCountry, originalLanguage = :originalLanguage, originalName = :originalName, overview = :overview, popularity = :popularity, posterPath = :posterPath, voteAverage = :voteAverage, voteCount = :voteCount, lastUpdate = :lastUpdate WHERE id = :id")
    abstract suspend fun update(
        backdropPath: String?,
        firstAirDate: Instant,
        genreList: List<Genre>,
        id: Long,
        name: String,
        originCountry: String,
        originalLanguage: String,
        originalName: String,
        overview: String,
        popularity: Double,
        posterPath: String?,
        voteAverage: Double,
        voteCount: Int,
        lastUpdate: Instant
    )

    /* @Query("DELETE FROM Show WHERE added = 0 AND lastUpdate < (strftime('%s','now') - (3 * 86400))")
     abstract suspend fun invalidate()*/

    @Query("DELETE FROM Show")
    abstract suspend fun deleteAll()
}
