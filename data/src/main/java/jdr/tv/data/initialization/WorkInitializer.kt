package jdr.tv.data.initialization

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import javax.inject.Inject
import jdr.tv.work.SyncWorker

class WorkInitializer @Inject constructor(private val context: Context) : Initializer {
    override fun initialize() {
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(SyncWorker::class.java.name, ExistingPeriodicWorkPolicy.KEEP, SyncWorker.createPeriodicWorkRequest())
        }
    }
}
