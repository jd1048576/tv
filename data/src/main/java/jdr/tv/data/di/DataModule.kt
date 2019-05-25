package jdr.tv.data.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Singleton
    @Provides
    @JvmStatic
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}