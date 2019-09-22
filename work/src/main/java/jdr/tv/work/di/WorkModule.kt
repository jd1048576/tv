package jdr.tv.work.di

import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkerFactory
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import jdr.tv.local.Database
import jdr.tv.remote.TmdbApi
import jdr.tv.work.SyncWorker
import jdr.tv.work.WorkerProviderFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import javax.inject.Singleton

@Module
object WorkModule {

    @Singleton
    @Provides
    @IntoSet
    @JvmStatic
    fun providesSyncWorkerFactory(database: Database, tmdbApi: TmdbApi): WorkerFactory {
        return WorkerProviderFactory(SyncWorker::class.java.name) { context, params ->
            SyncWorker(context, params, database, tmdbApi)
        }
    }

    @Singleton
    @Provides
    @JvmStatic
    fun providesWorkConfiguration(workerFactorySet: Set<@JvmSuppressWildcards WorkerFactory>): Configuration {
        return Configuration.Builder()
            .setExecutor(Dispatchers.IO.asExecutor())
            .setTaskExecutor(Dispatchers.IO.asExecutor())
            .setWorkerFactory(DelegatingWorkerFactory().apply { workerFactorySet.forEach(this::addFactory) })
            .build()
    }
}
