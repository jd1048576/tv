package jdr.tv.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import jdr.tv.data.local.entities.Episode
import jdr.tv.data.local.entities.EpisodeItem
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao : BaseDao<Episode> {

    @Transaction
    @Query("SELECT Episode.episodeNumber, Episode.name, Episode.id, Episode.seasonNumber, " +
        "Show.id as showId, Show.name as showName, Show.posterPath FROM Episode " +
        "JOIN Season ON Season.id = Episode.seasonId JOIN Show ON Show.id = Season.showId " +
        "WHERE Episode.airDate < strftime('%s','now') AND Show.id IN (SELECT `Add`.showId FROM `Add`) " +
        "AND Episode.id NOT IN (SELECT episodeId FROM Watch) " +
        "GROUP BY Show.id HAVING MIN(Episode.seasonNumber * 1000 + Episode.episodeNumber)")
    fun selectWatchList(): Flow<List<EpisodeItem>>

    @Query(
        "SELECT Episode.* FROM Episode " +
            "JOIN Season ON Episode.seasonId = Season.id " +
            "WHERE Season.showId = :showId " +
            "ORDER BY (Episode.seasonNumber * 1000 + Episode.episodeNumber) ASC"
    )
    fun selectList(showId: Long): Flow<List<Episode>>

    @Query(
        "SELECT Episode.id FROM Episode " +
            "JOIN Season ON Episode.seasonId = Season.id " +
            "WHERE Season.showId = :showId"
    )
    suspend fun selectIdList(showId: Long): List<@JvmSuppressWildcards Long>

    @Query("DELETE FROM Episode WHERE id IN (:idList)")
    suspend fun deleteIdList(idList: List<Long>)

    /*  @Query("SELECT Episode.*, Show.name AS showName, Show.posterPath AS showPosterPath, Show.launchId, Show.launchSource FROM Episode,
    Show WHERE Episode.showId = Show.id AND DATE(DATETIME(Episode.airDate / 1000 , 'unixepoch', 'localtime')) >= DATE(DATETIME('now', 'localtime'))
    GROUP BY Episode.showId HAVING MIN(Episode.airDate + Episode.episodeNumber) ORDER BY Episode.airDate ASC")
    LiveData<List<EpisodeItem>> getScheduleList();

    @Query("SELECT  Episode.*, Show.name AS showName, Show.posterPath AS showPosterPath, Show.launchId, Show.launchSource FROM Episode, Show
    WHERE Episode.showId = Show.id AND Episode.watched = 0
    AND DATETIME(Episode.airDate / 1000 , 'unixepoch', 'localtime') < DATETIME('now', 'localtime')
    GROUP BY Episode.showId HAVING MIN(Episode.airDate + Episode.episodeNumber) ORDER BY Episode.airDate DESC")
    LiveData<List<EpisodeItem>> getWatchList();*/
}
