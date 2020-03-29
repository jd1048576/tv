package jdr.tv.data.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import jdr.tv.common.log.Log
import jdr.tv.data.local.Database
import jdr.tv.data.local.entities.RelatedShow
import jdr.tv.data.local.entities.Show
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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.time.Duration

class SyncWorker(context: Context, params: WorkerParameters, private val database: Database, private val tmdbApi: TmdbApi) :
    CoroutineWorker(context, params) {

    companion object {

        private const val SYNC_PERIOD = 8L
        private const val MAX_REQUEST_SIZE = 20

        fun createPeriodicWorkRequest(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresDeviceIdle(true)
                .build()

            return PeriodicWorkRequestBuilder<SyncWorker>(Duration.ofHours(SYNC_PERIOD))
                .addTag(SyncWorker::class.java.name)
                .setConstraints(constraints)
                .build()
        }
    }

    override suspend fun doWork(): Result = withContext(IO) {
        applicationContext.sendNotification(1, "Sync Started")
        val start = System.currentTimeMillis()
        val addedList: List<Long> = database.addDao().selectAddedShowIdList()
        addedList.map { async { syncShow(it) } }.awaitAll()
        val end = System.currentTimeMillis()
        applicationContext.sendNotification(1, "Sync Completed, ${addedList.size} Shows Synced in ${end - start} ms")
        Result.success()
    }

    private suspend fun syncShow(showId: Long) {
        fetchDetailedShow(showId)
            .mergeSwitchMap { fetchSeasonList(showId, it.numberOfSeasons) }
            .onSuccess {
                insert(it.first, it.second)
            }
            .onFailure { Log.e(it, "Sync Worker Failure") }
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
