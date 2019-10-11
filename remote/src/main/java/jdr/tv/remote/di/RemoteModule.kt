package jdr.tv.remote.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides
import java.io.File
import java.time.Instant
import javax.inject.Named
import javax.inject.Singleton
import jdr.tv.remote.TmdbApi
import jdr.tv.remote.adapter.InstantAdapter
import jdr.tv.remote.adapter.RemoteGenreAdapter
import jdr.tv.remote.adapter.RemoteSeasonListAdapter
import jdr.tv.remote.entities.RemoteGenre
import jdr.tv.remote.entities.RemoteSeason
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.asExecutor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
object RemoteModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val CACHE_SIZE: Long = 10 * 1024 * 1024

    @Singleton
    @Provides
    @JvmStatic
    fun providesInterceptor(@Named("TMDB_API_KEY") tmdbApiKey: String) = Interceptor { chain ->
        chain.request().run {
            val url = url.newBuilder().addQueryParameter("api_key", tmdbApiKey).build()
            chain.proceed(newBuilder().url(url).build())
        }
    }

    @Singleton
    @Provides
    @Named("API")
    @JvmStatic
    fun providesApiOkHttpClient(@Named("DEFAULT") client: OkHttpClient, cache: File, interceptor: Interceptor): OkHttpClient {
        return client.newBuilder()
            .addInterceptor(interceptor)
            .cache(Cache(File(cache, "api"), CACHE_SIZE))
            .build()
    }

    @Singleton
    @Provides
    @JvmStatic
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
