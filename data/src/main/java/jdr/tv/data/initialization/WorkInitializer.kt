package jdr.tv.data.initialization

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import jdr.tv.work.SyncWorker
import javax.inject.Inject

class WorkInitializer @Inject constructor(private val context: Context) : Initializer {
    override fun initialize() {
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(SyncWorker::class.java.name, ExistingPeriodicWorkPolicy.KEEP, SyncWorker.createPeriodicWorkRequest())
        }
    }
}
