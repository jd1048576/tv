package jdr.tv.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import jdr.tv.base.di.Injector
import jdr.tv.local.Database
import jdr.tv.local.di.LocalModule
import jdr.tv.remote.TmdbApi
import jdr.tv.remote.di.RemoteModule
import jdr.tv.work.di.WorkModule

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
