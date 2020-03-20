package jdr.tv.feature.search.ui

import javax.inject.Inject
import jdr.tv.common.ui.Resource
import jdr.tv.common.ui.asSuccess
import jdr.tv.data.local.Database
import jdr.tv.data.local.entities.SearchItem
import jdr.tv.data.local.entities.Show
import jdr.tv.data.local.insertOrUpdate
import jdr.tv.data.mapper.toShow
import jdr.tv.data.remote.Request
import jdr.tv.data.remote.Response
import jdr.tv.data.remote.TmdbApi
import jdr.tv.data.remote.entities.RemoteShowList
import jdr.tv.data.remote.onFailure
import jdr.tv.data.remote.onSuccess
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class SearchRepository @Inject constructor(private val database: Database, private val tmdbApi: TmdbApi) {

    companion object {
        private const val PAGE_GAP = 100L
    }

    fun search(query: String): Flow<Resource<List<Show>>> {
        return flow {
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
