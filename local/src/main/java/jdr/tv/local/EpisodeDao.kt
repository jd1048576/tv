package jdr.tv.local

import androidx.room.Dao
import androidx.room.Query
import jdr.tv.local.entities.Episode
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EpisodeDao : BaseDao<Episode>() {

    @Query(
        "SELECT Episode.* FROM Episode " +
            "JOIN Season ON Episode.seasonId = Season.id " +
            "WHERE Season.showId = :showId " +
            "ORDER BY (Episode.seasonNumber * 1000 + Episode.episodeNumber) ASC"
    )
    abstract fun selectList(showId: Long): Flow<List<Episode>>

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
