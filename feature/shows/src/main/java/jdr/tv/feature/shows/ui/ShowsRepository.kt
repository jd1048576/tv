package jdr.tv.feature.shows.ui

import javax.inject.Inject
import jdr.tv.data.local.Database
import jdr.tv.data.local.entities.Show
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

@OptIn(ExperimentalCoroutinesApi::class)
class ShowsRepository @Inject constructor(private val database: Database) {

    fun selectAddedShowList(): Flow<List<Show>> {
        return database.addDao().selectAddedShowListFlow().flowOn(IO)
    }
}
