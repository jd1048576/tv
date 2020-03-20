package jdr.tv.feature.watchlist.ui

import jdr.tv.data.local.Database
import jdr.tv.data.local.entities.EpisodeItem
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class WatchListRepository @Inject constructor(private val database: Database) {

    fun selectWatchList(): Flow<List<EpisodeItem>> {
        return database.episodeDao().selectWatchList().flowOn(IO)
    }
}
