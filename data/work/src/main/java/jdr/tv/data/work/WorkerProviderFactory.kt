package jdr.tv.data.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class WorkerProviderFactory(private val className: String, private val provider: (context: Context, params: WorkerParameters) -> ListenableWorker) :
    WorkerFactory() {

    override fun createWorker(context: Context, workerClassName: String, params: WorkerParameters): ListenableWorker? {
        return if (className == workerClassName) provider(context, params) else null
    }
}
