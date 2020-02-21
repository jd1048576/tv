package jdr.tv.data.core.initialization

import android.content.Context
import androidx.work.WorkManager
import jdr.tv.work.SyncWorker
import jdr.tv.work.extensions.enqueueUniquePeriodicWork
import javax.inject.Inject

class WorkInitializer @Inject constructor(private val context: Context) : Initializer {
    override fun initialize() {
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork<SyncWorker>(SyncWorker.createPeriodicWorkRequest())
        }
    }
}
