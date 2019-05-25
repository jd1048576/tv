package jdr.tv.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import jdr.tv.local.Database
import jdr.tv.local.di.LocalModule
import jdr.tv.remote.TmdbApi
import jdr.tv.remote.di.RemoteModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, LocalModule::class, RemoteModule::class])
interface DataComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): DataComponent
    }

    fun client(): OkHttpClient
    fun database(): Database
    fun tmdbApi(): TmdbApi
}
