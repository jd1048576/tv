package jdr.tv.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

class BoundaryCallback<LOCAL, REMOTE>(
    private val scope: CoroutineScope,
    private val request: (Int) -> Request<REMOTE>?,
    private val insert: suspend (REMOTE) -> Unit,
    private val currentPageFunction: suspend (LOCAL) -> Int?,
    private val totalPageFunction: (REMOTE) -> Int
) : PagedList.BoundaryCallback<LOCAL>() {

    private val inProgress: MutableSet<Int> = ConcurrentHashMap.newKeySet<Int>()
    private val completed: MutableSet<Int> = ConcurrentHashMap.newKeySet<Int>()

    private var totalPages = 0

    val loading = MutableLiveData<Unit>()
    val error = MutableLiveData<Exception>()

    override fun onZeroItemsLoaded() {
        inProgress.clear()
        completed.clear()
        load()
    }

    override fun onItemAtEndLoaded(itemAtEnd: LOCAL) {
        load(itemAtEnd)
    }

    private fun load(itemAtEnd: LOCAL? = null) = scope.launch {
        val page: Int = itemAtEnd?.let { currentPageFunction(it) }?.takeIf { it < totalPages }?.plus(1) ?: 1
        if (!inProgress.contains(page) && !completed.contains(page)) {
            request(page)?.apply {
                loading.value = Unit
                val result = try {
                    inProgress.add(page)
                    execute()
                } finally {
                    withContext(NonCancellable) {
                        inProgress.remove(page)
                    }
                }
                result.onSuccess {
                    completed.add(page)
                    totalPages = totalPageFunction(it)
                    insert(it)
                }.onError { error.value = it }
            }
        }
    }
}