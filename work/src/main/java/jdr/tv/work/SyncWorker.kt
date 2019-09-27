package jdr.tv.work

import android.content.Context
import androidx.room.withTransaction
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import jdr.tv.base.Log
import jdr.tv.local.Database
import jdr.tv.local.entities.RelatedShow
import jdr.tv.local.entities.Show
import jdr.tv.local.insertOrUpdate
import jdr.tv.mapper.toDetails
import jdr.tv.mapper.toEpisodeList
import jdr.tv.mapper.toSeason
import jdr.tv.mapper.toShow
import jdr.tv.remote.Request
import jdr.tv.remote.Response
import jdr.tv.remote.TmdbApi
import jdr.tv.remote.entities.RemoteDetailedShow
import jdr.tv.remote.entities.RemoteSeason
import jdr.tv.remote.execute
import jdr.tv.remote.map
import jdr.tv.remote.mergeSwitchMap
import jdr.tv.remote.onFailure
import jdr.tv.remote.onSuccess
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.time.Duration

class SyncWorker(context: Context, params: WorkerParameters, private val database: Database, private val tmdbApi: TmdbApi) :
    CoroutineWorker(context, params) {

    companion object {

        private const val SYNC_PERIOD = 12L
        private const val MAX_REQUEST_SIZE = 20

        fun createPeriodicWorkRequest(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresDeviceIdle(true)
                .setRequiresBatteryNotLow(true)
                .build()

            return PeriodicWorkRequestBuilder<SyncWorker>(Duration.ofHours(SYNC_PERIOD))
                .addTag(SyncWorker::class.java.name)
                .setConstraints(constraints)
                .build()
        }
    }

    override suspend fun doWork(): Result = withContext(IO) {
        database.addDao().selectAddedShowIdList().map { async { syncShow(it) } }.awaitAll()
        Result.success()
    }

    private suspend fun syncShow(showId: Long) {
        fetchDetailedShow(showId)
            .mergeSwitchMap { fetchSeasonList(showId, it.numberOfSeasons) }
            .onSuccess {
                insert(it.first, it.second)
            }
            .onFailure { Log.e(it) }
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

    private suspend fun insert(remoteDetailedShow: RemoteDetailedShow, remoteSeasonList: List<RemoteSeason>) {
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
}
