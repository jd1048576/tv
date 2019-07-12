package jdr.tv.search.ui

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.withTransaction
import jdr.tv.base.Dispatchers.IO
import jdr.tv.base.Dispatchers.IOExecutor
import jdr.tv.data.BoundaryCallback
import jdr.tv.data.PaginatedResult
import jdr.tv.data.Request
import jdr.tv.data.mapper.toShow
import jdr.tv.data.toRequest
import jdr.tv.local.Database
import jdr.tv.local.entities.SearchItem
import jdr.tv.local.entities.Show
import jdr.tv.local.insertOrUpdate
import jdr.tv.remote.TmdbApi
import jdr.tv.remote.entities.RemoteShowList
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchRepository @Inject constructor(private val database: Database, private val tmdbApi: TmdbApi) {

    companion object {
        @JvmField
        val pageConfig = PagedList.Config.Builder().setPageSize(20).setPrefetchDistance(60).setEnablePlaceholders(true).build()
    }

    fun search(context: CoroutineContext, query: () -> String): PaginatedResult<Show> {
        val factory = database.searchItemDao().selectSearchDataSourceFactory()

        val boundaryCallback = BoundaryCallback(
            context,
            { createRequest(it, query) },
            this::insert,
            this::selectPageForShowItem,
            RemoteShowList::totalPages
        )

        val pagedList = LivePagedListBuilder(factory, pageConfig)
            .setFetchExecutor(IOExecutor)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return PaginatedResult(pagedList, boundaryCallback.loading, boundaryCallback.failure, boundaryCallback::onZeroItemsLoaded)
    }

    private suspend fun insert(remoteShowList: RemoteShowList) = withContext(IO) {
        val page = remoteShowList.page
        val showList = remoteShowList.results.map { it.toShow() }
        val searchItemList = showList.mapIndexed { index, show -> SearchItem(page * 100L + index, show.id, page) }
        database.withTransaction {
            if (remoteShowList.page == 1) database.searchItemDao().deleteAll()
            database.showDao().insertOrUpdate(showList)
            database.searchItemDao().insertOrUpdate(searchItemList)
        }
    }

    private fun createRequest(page: Int, query: () -> String): Request<RemoteShowList>? {
        return query().takeUnless { it.isEmpty() }?.let { tmdbApi::search.toRequest(page, it) }
    }

    private suspend fun selectPageForShowItem(show: Show): Int? = withContext(IO) {
        database.searchItemDao().selectSearchItemPage(show.id)
    }

    suspend fun deleteAll() = withContext(IO) { database.searchItemDao().deleteAll() }
}