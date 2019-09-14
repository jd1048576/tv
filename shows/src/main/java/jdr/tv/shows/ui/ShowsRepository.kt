package jdr.tv.shows.ui

import jdr.tv.local.Database
import jdr.tv.local.entities.Show
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ShowsRepository @Inject constructor(private val database: Database) {

    fun selectAddedShowList(): Flow<List<Show>> {
        return database.addDao().selectAddedShowListFlow().flowOn(IO)
    }
}
