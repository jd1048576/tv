package jdr.tv.data.core.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides
import jdr.tv.data.core.BuildConfig
import jdr.tv.data.remote.TmdbApi
import jdr.tv.data.remote.adapter.InstantAdapter
import jdr.tv.data.remote.adapter.RemoteGenreAdapter
import jdr.tv.data.remote.adapter.RemoteSeasonListAdapter
import jdr.tv.data.remote.entities.RemoteGenre
import jdr.tv.data.remote.entities.RemoteSeason
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.asExecutor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.File
import java.time.Instant
import javax.inject.Named
import javax.inject.Singleton

@Module
object RemoteModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val CACHE_SIZE: Long = 10 * 1024 * 1024

    @Singleton
    @Provides
    @Named("TMDB_API_KEY")
    fun providesTmdbApiKey(): String {
        return BuildConfig.TMDB_API_KEY
    }

    @Singleton
    @Provides
    fun providesInterceptor(@Named("TMDB_API_KEY") tmdbApiKey: String) = Interceptor { chain ->
        chain.request().run {
            val url = url.newBuilder().addQueryParameter("api_key", tmdbApiKey).build()
            chain.proceed(newBuilder().url(url).build())
        }
    }

    @Singleton
    @Provides
    @Named("API")
    fun providesApiOkHttpClient(@Named("DEFAULT") client: OkHttpClient, cache: File, interceptor: Interceptor): OkHttpClient {
        return client.newBuilder()
            .addInterceptor(interceptor)
            .cache(Cache(File(cache, "api"), CACHE_SIZE))
            .build()
    }

    @Singleton
    @Provides
    fun providesTmdbApi(@Named("API") client: OkHttpClient): TmdbApi {
        val moshiInstant: Moshi = Moshi.Builder()
            .add(Instant::class.java, InstantAdapter())
            .build()

        val moshi: Moshi = moshiInstant.newBuilder()
            .add(RemoteGenre::class.java, RemoteGenreAdapter())
            .add(Types.newParameterizedType(List::class.java, RemoteSeason::class.java), RemoteSeasonListAdapter(moshiInstant))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .callbackExecutor(IO.asExecutor())
            .build()
            .create()
    }
}
