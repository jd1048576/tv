package jdr.tv.details.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.room.withTransaction
import jdr.tv.base.Dispatchers.IO
import jdr.tv.base.extensions.olderThan
import jdr.tv.data.*
import jdr.tv.data.mapper.toDetails
import jdr.tv.data.mapper.toEpisodeList
import jdr.tv.data.mapper.toSeason
import jdr.tv.data.mapper.toShow
import jdr.tv.local.Database
import jdr.tv.local.entities.DetailedShow
import jdr.tv.local.entities.RelatedShow
import jdr.tv.local.entities.Show
import jdr.tv.local.insertOrUpdate
import jdr.tv.remote.TmdbApi
import jdr.tv.remote.entities.RemoteDetailedShow
import jdr.tv.remote.entities.RemoteSeason
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val database: Database, private val tmdbApi: TmdbApi) {

    fun selectDetailedShow(showId: Long): LiveData<Result<DetailedShow>> {
        return liveData {
            if (shouldUpdate(showId)) {
                val disposable = emitSource(database.detailsDao().selectDetailedShowLiveData(showId).map { Result.loading(it) })
                fetchDetailedShow(showId)
                    .onSuccess { detailedShow ->
                        fetchSeasonList(showId, detailedShow.numberOfSeasons)
                            .onSuccess {
                                disposable.dispose()
                                insert(detailedShow, it.flatten())
                                emitSource(database.detailsDao().selectDetailedShowLiveData(showId).map { Result.success(it) })
                            }
                            .onFailure { emit(Result.error<DetailedShow>(it)) }
                    }
                    .onFailure { emit(Result.error<DetailedShow>(it)) }
            } else {
                emitSource(database.detailsDao().selectDetailedShowLiveData(showId).map { Result.success(it) })
            }
        }
    }

    private suspend fun shouldUpdate(showId: Long): Boolean = withContext(IO) {
        val lastUpdate: Instant = database.detailsDao().selectLastDetailsUpdate(showId)
        val seasonCount: Int = database.seasonDao().selectSeasonCount(showId)
        lastUpdate.olderThan(days = 1) || seasonCount == 0
    }

    private suspend fun fetchDetailedShow(showId: Long): Result<RemoteDetailedShow> {
        val query = mapOf("append_to_response" to "credits,content_ratings,external_ids,recommendations,similar,videos")
        return tmdbApi::show.toRequest(showId, query).execute()
    }

    private suspend fun fetchSeasonList(showId: Long, numberOfSeasons: Int): Result<List<List<RemoteSeason>>> {
        val requestList = ArrayList<Request<List<RemoteSeason>>>()
        val seasonList = IntRange(1, numberOfSeasons).map { i -> "season/$i" }.toMutableList()

        while (seasonList.isNotEmpty()) {
            val limit = if (seasonList.size >= 20) 20 else seasonList.size
            val subList = seasonList.subList(0, limit)
            val seasons = subList.joinToString(",")
            requestList.add(tmdbApi::season.toRequest(showId, seasons))
            seasonList.removeAll(subList)
        }
        return requestList.execute()
    }

    private suspend fun insert(remoteDetailedShow: RemoteDetailedShow, remoteSeasonList: List<RemoteSeason>) = withContext(IO) {
        val show = remoteDetailedShow.toShow()
        val details = remoteDetailedShow.toDetails()

        val recommendedShowList = remoteDetailedShow.recommendations.results.map { it.toShow() }
        val similarShowList = remoteDetailedShow.similar.results.map { it.toShow() }

        val showList = ArrayList<Show>().apply {
            add(show)
            addAll(recommendedShowList)
            addAll(similarShowList)
        }

        val relatedShowList = ArrayList<RelatedShow>().apply {
            addAll(recommendedShowList.map { RelatedShow.recommended(show.id, it.id) })
            addAll(similarShowList.map { RelatedShow.similar(show.id, it.id) })
        }

        val seasonList = remoteSeasonList.map { it.toSeason() }
        val episodeList = remoteSeasonList.flatMap { it.toEpisodeList() }

        database.withTransaction {
            database.showDao().insertOrUpdate(showList)
            database.detailsDao().insertOrUpdate(details)
            database.relatedShowDao().insertOrUpdate(relatedShowList)
            database.seasonDao().insertOrUpdate(seasonList)
            database.episodeDao().insertOrUpdate(episodeList)
        }
    }
}