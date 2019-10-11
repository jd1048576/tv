package jdr.tv.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
import javax.inject.Singleton
import jdr.tv.data.BuildConfig
import jdr.tv.remote.extensions.asExecutorService
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient

@Module
object DataModule {

    private const val CACHE_SIZE: Long = 100 * 1024 * 1024

    @Singleton
    @Provides
    @Named("TMDB_API_KEY")
    @JvmStatic
    fun providesTmdbApiKey(): String {
        return BuildConfig.TMDB_API_KEY
    }

    @Singleton
    @Provides
    @JvmStatic
    fun providesCacheDir(context: Context): File {
        return context.cacheDir
    }

    @Singleton
    @Provides
    @Named("DEFAULT")
    @JvmStatic
    fun providesDefaultOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .dispatcher(Dispatcher(Dispatchers.IO.asExecutorService()))
            .build()
    }

    @Singleton
    @Provides
    @Named("IMAGE")
    @JvmStatic
    fun providesImageOkHttpClient(@Named("DEFAULT") client: OkHttpClient, cache: File): OkHttpClient {
        return client.newBuilder()
            .cache(Cache(File(cache, "image"), CACHE_SIZE))
            .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}
