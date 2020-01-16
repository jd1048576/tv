package jdr.tv.data.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import jdr.tv.data.local.Database
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.asExecutor

@Module
object LocalModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): RoomDatabase {
        return Room.databaseBuilder(context, RoomDatabase::class.java, "TV")
            .setQueryExecutor(IO.asExecutor())
            .setTransactionExecutor(IO.asExecutor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesDatabase(database: RoomDatabase): Database = database
}
