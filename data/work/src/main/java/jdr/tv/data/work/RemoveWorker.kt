package jdr.tv.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import jdr.tv.data.local.Database
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.Instant

class RemoveWorker(context: Context, params: WorkerParameters, private val database: Database) : CoroutineWorker(context, params) {

    companion object {
        private const val OLDER_THAN = 7L
        private const val SYNC_PERIOD = 24L

        fun createPeriodicWorkRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<RemoveWorker>(Duration.ofHours(SYNC_PERIOD))
                .addTag(RemoveWorker::class.java.name)
                .build()
        }
    }

    override suspend fun doWork(): Result = withContext(IO) {
        database.showDao().deleteAll(Instant.now().minus(Duration.ofDays(OLDER_THAN)))
        Result.success()
    }
}
