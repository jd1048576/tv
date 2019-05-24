package jdr.tv.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import jdr.tv.base.extensions.INSTANT_ZERO
import jdr.tv.local.entities.Cast
import jdr.tv.local.entities.Crew
import jdr.tv.local.entities.Episode
import java.time.Instant

@Dao
abstract class EpisodeDao : BaseDao<Episode>() {

    @Query("SELECT Episode.* FROM Episode JOIN Season ON Episode.seasonId = Season.id WHERE Season.showId = :showId ORDER BY (Episode.seasonNumber * 1000 + Episode.episodeNumber) ASC")
    abstract fun selectListLiveData(showId: Long): LiveData<List<Episode>>

    @Query("UPDATE Episode SET watched = :watched, watchedAt = :watchedAt WHERE id = :id")
    abstract suspend fun updateWatched(id: Long, watched: Boolean, watchedAt: Instant)

    @Transaction
    open suspend fun updateWatched(episodeList: List<Episode>) {
        val instant = Instant.now()
        episodeList.forEach { updateWatched(it.id, !it.watched, if (it.watched) INSTANT_ZERO else instant) }
    }

    @Query("UPDATE Episode SET watched = 0, watchedAt = 0 WHERE seasonId IN (SELECT seasonId FROM Season WHERE showId = :showId)")
    abstract suspend fun updateWatchedShow(showId: Long)

    @Query("UPDATE Episode SET airDate = :airDate, episodeNumber = :episodeNumber, name = :name, overview = :overview, seasonId = :seasonId, seasonNumber = :seasonNumber, stillPath = :stillPath, voteAverage = :voteAverage, voteCount = :voteCount, crewList = :crewList, guestStarList = :guestStarList WHERE id = :id")
    abstract suspend fun updateEpisode(
        airDate: Instant,
        episodeNumber: Int,
        id: Long,
        name: String,
        overview: String,
        seasonId: Long,
        seasonNumber: Int,
        stillPath: String,
        voteAverage: Double,
        voteCount: Int,
        crewList: List<Crew>,
        guestStarList: List<Cast>
    )

    @Transaction
    open suspend fun insertOrUpdate(item: Episode) = with(item) {
        val insertResult = insert(this)
        if (insertResult == -1L) {
            updateEpisode(
                airDate,
                episodeNumber,
                id,
                name,
                overview,
                seasonId,
                seasonNumber,
                stillPath,
                voteAverage,
                voteCount,
                crewList,
                guestStarList
            )
        }
    }

    @Transaction
    open suspend fun insertOrUpdate(itemList: List<Episode>) {
        val insertResult = insert(itemList)
        val updateList = insertResult.asSequence().withIndex().filter { it.value == -1L }.map { itemList[it.index] }.toList()

        if (updateList.isNotEmpty()) {
            updateList.forEach { insertOrUpdate(it) }
        }
    }

    /*  @Query("SELECT Episode.*, Show.name AS showName, Show.posterPath AS showPosterPath, Show.launchId, Show.launchSource FROM Episode, Show WHERE Episode.showId = Show.id AND DATE(DATETIME(Episode.airDate / 1000 , 'unixepoch', 'localtime')) >= DATE(DATETIME('now', 'localtime')) GROUP BY Episode.showId HAVING MIN(Episode.airDate + Episode.episodeNumber) ORDER BY Episode.airDate ASC")
    LiveData<List<EpisodeItem>> getScheduleList();

    @Query("SELECT  Episode.*, Show.name AS showName, Show.posterPath AS showPosterPath, Show.launchId, Show.launchSource FROM Episode, Show WHERE Episode.showId = Show.id AND Episode.watched = 0 AND DATETIME(Episode.airDate / 1000 , 'unixepoch', 'localtime') < DATETIME('now', 'localtime') GROUP BY Episode.showId HAVING MIN(Episode.airDate + Episode.episodeNumber) ORDER BY Episode.airDate DESC")
    LiveData<List<EpisodeItem>> getWatchList();*/
}
