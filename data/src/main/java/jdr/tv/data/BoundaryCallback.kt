package jdr.tv.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

class BoundaryCallback<LOCAL, REMOTE>(
    private val context: CoroutineContext,
    private val request: (Int) -> Request<REMOTE>?,
    private val insert: suspend (REMOTE) -> Unit,
    private val currentPageFunction: suspend (LOCAL) -> Int?,
    private val totalPageFunction: (REMOTE) -> Int
) : PagedList.BoundaryCallback<LOCAL>() {

    private val supervisor = SupervisorJob()
    private val inProgress: MutableSet<Int> = ConcurrentHashMap.newKeySet()
    private val completed: MutableSet<Int> = ConcurrentHashMap.newKeySet()

    private var totalPages = 0

    val loading = MutableLiveData<Boolean>()
    val failure = MutableLiveData<Exception>()

    override fun onZeroItemsLoaded() {
        supervisor.cancelChildren()
        inProgress.clear()
        completed.clear()
        totalPages = 0
        load()
    }

    override fun onItemAtEndLoaded(itemAtEnd: LOCAL) {
        load(itemAtEnd)
    }

    private fun load(itemAtEnd: LOCAL? = null) = CoroutineScope(context + supervisor).launch {
        val page: Int = itemAtEnd?.let { currentPageFunction(it) }?.takeIf { it < totalPages }?.plus(1) ?: 1
        if (!inProgress.contains(page) && !completed.contains(page)) {
            request(page)?.also { executeRequest(page, it) }
        }
    }

    private suspend fun executeRequest(page: Int, request: Request<REMOTE>) {
        loading.value = true
        val result = try {
            inProgress.add(page)
            request.execute()
        } finally {
            inProgress.remove(page)
            if (inProgress.size == 0) loading.value = false
        }
        handleResult(page, result)
    }

    private suspend fun handleResult(page: Int, result: Response<REMOTE>) = with(result) {
        onSuccess {
            completed.add(page)
            totalPages = totalPageFunction(it)
            insert(it)
        }
        onFailure { failure.value = it }
    }
}
