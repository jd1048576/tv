package jdr.tv.search.ui

import androidx.room.withTransaction
import javax.inject.Inject
import jdr.tv.local.Database
import jdr.tv.local.entities.SearchItem
import jdr.tv.local.entities.Show
import jdr.tv.local.insertOrUpdate
import jdr.tv.mapper.toShow
import jdr.tv.remote.Request
import jdr.tv.remote.Response
import jdr.tv.remote.TmdbApi
import jdr.tv.remote.entities.RemoteShowList
import jdr.tv.remote.onFailure
import jdr.tv.remote.onSuccess
import jdr.tv.ui.Resource
import jdr.tv.ui.asSuccess
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SearchRepository @Inject constructor(private val database: Database, private val tmdbApi: TmdbApi) {

    companion object {
        private const val PAGE_GAP = 100L
    }

    fun search(query: String): Flow<Resource<List<Show>>> {
        return flow<Resource<List<Show>>> {
            emit(Resource.loading(database.searchItemDao().selectSearchShowList()))
            fetchSearch(query)
                .onSuccess {
                    if (it.results.isEmpty()) {
                        deleteAll()
                        emit(Resource.success(emptyList()))
                    } else {
                        insert(it)
                        emitAll(database.searchItemDao().selectSearchShowListFlow().asSuccess())
                    }
                }
                .onFailure { emit(Resource.failure(it)) }
        }.flowOn(IO)
    }

    private suspend fun fetchSearch(query: String): Response<RemoteShowList> {
        return Request { tmdbApi.search(1, query) }.execute()
    }

    private suspend fun insert(remoteShow: RemoteShowList) = withContext(IO) {
        val page = remoteShow.page
        val showList = ArrayList<Show>()
        val searchItemList = ArrayList<SearchItem>()

        remoteShow.results.forEachIndexed { index, remoteShow ->
            showList.add(remoteShow.toShow())
            searchItemList.add(SearchItem(page * PAGE_GAP + index, remoteShow.id, page))
        }

        database.withTransaction {
            database.searchItemDao().deleteAll()
            database.showDao().insertOrUpdate(showList)
            database.searchItemDao().insertOrUpdate(searchItemList)
        }
    }

    suspend fun deleteAll() = withContext(IO) { database.searchItemDao().deleteAll() }
}
