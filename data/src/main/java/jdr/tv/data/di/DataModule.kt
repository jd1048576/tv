package jdr.tv.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import jdr.tv.data.BuildConfig
import javax.inject.Named
import javax.inject.Singleton

@Module
object DataModule {

    @Singleton
    @Provides
    @Named("TMDB_API_KEY")
    @JvmStatic
    fun provideTmdbApiKey(): String {
        return BuildConfig.TMDB_API_KEY
    }

    @Singleton
    @Provides
    @JvmStatic
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}
