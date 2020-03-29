package jdr.tv.data.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import jdr.tv.data.local.Database
import jdr.tv.data.remote.TmdbApi
import jdr.tv.data.work.di.WorkModule

@Singleton
@Component(modules = [DataModule::class, InitializationModule::class, LocalModule::class, RemoteModule::class, WorkModule::class])
interface DataComponent : Injector<DataComponentApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    fun database(): Database
    fun tmdbApi(): TmdbApi
}
