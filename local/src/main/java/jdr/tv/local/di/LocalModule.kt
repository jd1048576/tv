package jdr.tv.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import jdr.tv.base.Dispatchers.IOExecutor
import jdr.tv.local.Database
import javax.inject.Singleton

@Module
object LocalModule {

    @Singleton
    @Provides
    @JvmStatic
    fun providesAppDatabase(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "TV")
            .setQueryExecutor(IOExecutor)
            .setTransactionExecutor(IOExecutor)
            .fallbackToDestructiveMigration()
            .build()
    }
}
