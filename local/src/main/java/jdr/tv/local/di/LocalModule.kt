package jdr.tv.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import jdr.tv.local.Database
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.asExecutor
import javax.inject.Singleton

@Module
object LocalModule {

    @Singleton
    @Provides
    @JvmStatic
    fun providesAppDatabase(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "TV")
            .setQueryExecutor(IO.asExecutor())
            .setTransactionExecutor(IO.asExecutor())
            .fallbackToDestructiveMigration()
            .build()
    }
}
