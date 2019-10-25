package jdr.tv.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import jdr.tv.local.Database
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.asExecutor

@Module
object LocalModule {

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "TV")
            .setQueryExecutor(IO.asExecutor())
            .setTransactionExecutor(IO.asExecutor())
            .fallbackToDestructiveMigration()
            .build()
    }
}
