package jdr.tv.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import jdr.tv.data.initialization.AppInitializer
import jdr.tv.local.Database
import jdr.tv.local.di.LocalModule
import jdr.tv.remote.TmdbApi
import jdr.tv.remote.di.RemoteModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, InitializationModule::class, LocalModule::class, RemoteModule::class])
interface DataComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    fun appInitializer(): AppInitializer
    fun client(): OkHttpClient
    fun database(): Database
    fun tmdbApi(): TmdbApi
}
