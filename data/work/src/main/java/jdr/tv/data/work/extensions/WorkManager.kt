package jdr.tv.data.work.extensions

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager

inline fun <reified T : ListenableWorker> WorkManager.enqueueUniquePeriodicWork(
    periodicWork: PeriodicWorkRequest,
    existingPeriodicWorkPolicy: ExistingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP
) {
    enqueueUniquePeriodicWork(T::class.java.name, existingPeriodicWorkPolicy, periodicWork)
}
