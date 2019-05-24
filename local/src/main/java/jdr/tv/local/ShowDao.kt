package jdr.tv.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import jdr.tv.local.entities.Genre
import jdr.tv.local.entities.Show
import java.time.Instant

@Dao
abstract class ShowDao : BaseDao<Show>() {

    @Query("SELECT * FROM Show WHERE id = :id")
    abstract fun selectLiveData(id: Long): LiveData<Show>

    @Query("SELECT * FROM Show WHERE added = 1 ORDER BY name ASC")
    abstract fun selectAddedShowListDataSourceFactory(): DataSource.Factory<Int, Show>

    @Query("UPDATE Show SET added = :added WHERE id = :id")
    abstract fun updateAdded(id: Long, added: Boolean)

    @Query("UPDATE Show SET backdropPath = :backdropPath, firstAirDate = :firstAirDate, genreList = :genreList, name = :name, originCountry = :originCountry, originalLanguage = :originalLanguage, originalName = :originalName, overview = :overview, popularity = :popularity, posterPath = :posterPath, voteAverage = :voteAverage, voteCount = :voteCount, lastUpdate = :lastUpdate WHERE id = :id")
    abstract suspend fun updateShow(
        backdropPath: String,
        firstAirDate: Instant,
        genreList: List<Genre>,
        id: Long,
        name: String,
        originCountry: String,
        originalLanguage: String,
        originalName: String,
        overview: String,
        popularity: Double,
        posterPath: String,
        voteAverage: Double,
        voteCount: Int,
        lastUpdate: Instant
    )

    @Transaction
    open suspend fun insertOrUpdate(item: Show) = with(item) {
        val insertResult = insert(this)
        if (insertResult == -1L) {
            updateShow(
                backdropPath,
                firstAirDate,
                genreList,
                id,
                name,
                originCountry,
                originalLanguage,
                originalName,
                overview,
                popularity,
                posterPath,
                voteAverage,
                voteCount,
                lastUpdate
            )
        }
    }

    @Transaction
    open suspend fun insertOrUpdate(itemList: List<Show>) {
        val insertResult = insert(itemList)
        val updateList = insertResult.asSequence().withIndex().filter { it.value == -1L }.map { itemList[it.index] }.toList()

        if (updateList.isNotEmpty()) {
            updateList.forEach { insertOrUpdate(it) }
        }
    }

    @Query("DELETE FROM Show WHERE added = 0 AND lastUpdate < (strftime('%s','now') - (3 * 86400))")
    abstract suspend fun invalidate()

    @Query("DELETE FROM Show")
    abstract suspend fun deleteAll()
}
