package jdr.tv.feature.details.ui

import jdr.tv.common.extensions.olderThan
import jdr.tv.common.ui.Resource
import jdr.tv.common.ui.asSuccess
import jdr.tv.data.local.Database
import jdr.tv.data.local.entities.Add
import jdr.tv.data.local.entities.DetailedSeason
import jdr.tv.data.local.entities.DetailedShow
import jdr.tv.data.local.entities.RelatedShow
import jdr.tv.data.local.entities.Show
import jdr.tv.data.local.entities.Watch
import jdr.tv.data.local.insertOrUpdate
import jdr.tv.data.mapper.toDetails
import jdr.tv.data.mapper.toEpisodeList
import jdr.tv.data.mapper.toSeason
import jdr.tv.data.mapper.toShow
import jdr.tv.data.remote.Request
import jdr.tv.data.remote.Response
import jdr.tv.data.remote.TmdbApi
import jdr.tv.data.remote.entities.RemoteDetailedShow
import jdr.tv.data.remote.entities.RemoteSeason
import jdr.tv.data.remote.execute
import jdr.tv.data.remote.map
import jdr.tv.data.remote.mergeSwitchMap
import jdr.tv.data.remote.onFailure
import jdr.tv.data.remote.onSuccess
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsRepository @Inject constructor(private val database: Database, private val tmdbApi: TmdbApi) {

    companion object {
        private const val MAX_REQUEST_SIZE = 20
    }

    fun selectShow(showId: Long): Flow<Resource<Show>> {
        return flow {
            if (shouldUpdate(showId)) {
                emit(Resource.loading(database.showDao().select(showId)))
                fetchDetailedShow(showId)
                    .mergeSwitchMap { fetchSeasonList(showId, it.numberOfSeasons) }
                    .onSuccess {
                        insert(it.first, it.second)
                        emitAll(database.showDao().selectFlow(showId).asSuccess())
                    }
                    .onFailure { emit(Resource.failure(it)) }
            } else {
                emitAll(database.showDao().selectFlow(showId).asSuccess())
            }
        }.flowOn(IO)
    }

    fun selectAdded(showId: Long): Flow<Boolean> {
        return database.addDao().selectCount(showId).map { it == 1 }.flowOn(IO)
    }

    fun selectDetailedShow(showId: Long): Flow<DetailedShow> {
        return database.detailsDao().selectDetailedShowFlow(showId).filterNotNull().flowOn(IO)
    }

    fun selectDetailedSeasonList(showId: Long): Flow<List<DetailedSeason>> {
        return database.seasonDao().selectDetailedSeasonListFlow(showId).flowOn(IO)
    }

    private suspend fun shouldUpdate(showId: Long): Boolean = withContext(IO) {
        val lastUpdate: Instant = database.detailsDao().selectUpdatedAt(showId)
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
        val episodeIdListToRemove = database.episodeDao().selectIdList(show.id).toMutableList().apply {
            removeAll(episodeList.map { it.id })
        }

        database.withTransaction {
            database.showDao().insertOrUpdate(showList)
            database.detailsDao().insertOrUpdate(details)
            database.relatedShowDao().insertOrUpdate(relatedShowList)
            database.seasonDao().insertOrUpdate(seasonList)
            database.episodeDao().insertOrUpdate(episodeList)
            database.episodeDao().deleteIdList(episodeIdListToRemove)
        }
    }

    suspend fun updateAdded(showId: Long, added: Boolean) = withContext(IO) {
        if (added) {
            database.addDao().insert(Add(showId, Instant.now()))
        } else {
            database.addDao().delete(showId)
        }
    }

    suspend fun updateSeasonWatched(detailedSeason: DetailedSeason) = withContext(IO) {
        if (detailedSeason.watchCount != detailedSeason.episodeList.size) {
            val now = Instant.now()
            database.watchDao().insert(detailedSeason.episodeList.map { Watch(it.episode.id, now) })
        } else {
            database.watchDao().deleteAll(detailedSeason.episodeList.map { it.episode.id })
        }
    }
}
