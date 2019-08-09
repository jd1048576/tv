package jdr.tv.details.ui

import androidx.room.withTransaction
import jdr.tv.base.Dispatchers.IO
import jdr.tv.base.extensions.olderThan
import jdr.tv.data.Request
import jdr.tv.data.Resource
import jdr.tv.data.Response
import jdr.tv.data.asSuccess
import jdr.tv.data.execute
import jdr.tv.data.map
import jdr.tv.data.mapper.toDetails
import jdr.tv.data.mapper.toEpisodeList
import jdr.tv.data.mapper.toSeason
import jdr.tv.data.mapper.toShow
import jdr.tv.data.mergeSwitchMap
import jdr.tv.data.onFailure
import jdr.tv.data.onSuccess
import jdr.tv.local.Database
import jdr.tv.local.entities.DetailedShow
import jdr.tv.local.entities.RelatedShow
import jdr.tv.local.entities.Show
import jdr.tv.local.insertOrUpdate
import jdr.tv.remote.TmdbApi
import jdr.tv.remote.entities.RemoteDetailedShow
import jdr.tv.remote.entities.RemoteSeason
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val database: Database, private val tmdbApi: TmdbApi) {

    companion object {
        private const val MAX_REQUEST_SIZE = 20
    }

    fun selectDetailedShow(showId: Long): Flow<Resource<DetailedShow>> {
        return flow {
            if (shouldUpdate(showId)) {
                emit(Resource.loading(database.detailsDao().selectDetailedShow(showId)))
                fetchDetailedShow(showId)
                    .mergeSwitchMap { fetchSeasonList(showId, it.numberOfSeasons) }
                    .onSuccess {
                        insert(it.first, it.second)
                        emitAll(database.detailsDao().selectDetailedShowFlow(showId).asSuccess())
                    }
                    .onFailure { emit(Resource.failure(it)) }
            } else {
                emitAll(database.detailsDao().selectDetailedShowFlow(showId).asSuccess())
            }
        }
    }

    private suspend fun shouldUpdate(showId: Long): Boolean = withContext(IO) {
        val lastUpdate: Instant = database.detailsDao().selectLastDetailsUpdate(showId)
        val seasonCount: Int = database.seasonDao().selectSeasonCount(showId)
        lastUpdate.olderThan(days = 1) || seasonCount == 0
    }

    private suspend fun fetchDetailedShow(showId: Long): Response<RemoteDetailedShow> {
        val query = mapOf("append_to_response" to "credits,content_ratings,external_ids,recommendations,similar,videos")
        return Request { tmdbApi.show(showId, query) }.execute()
    }

    private suspend fun fetchSeasonList(showId: Long, numberOfSeasons: Int): Response<List<RemoteSeason>> {
        return IntRange(1, numberOfSeasons)
            .map { i -> "season/$i" }
            .chunked(MAX_REQUEST_SIZE)
            .map { Request { tmdbApi.season(showId, it.joinToString(",")) } }
            .execute()
            .map { it.flatten() }
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
