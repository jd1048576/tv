package jdr.tv.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

class BoundaryCallback<LOCAL, REMOTE>(
    private val scope: CoroutineScope,
    private val request: (Int) -> (suspend () -> Response<REMOTE>)?,
    private val insert: suspend (REMOTE) -> Unit,
    private val currentPageFunction: suspend (LOCAL) -> Int?,
    private val totalPageFunction: (REMOTE) -> Int
) : PagedList.BoundaryCallback<LOCAL>() {

    private var totalPages = 0

    val loading = MutableLiveData<Unit>()
    val error = MutableLiveData<Exception>()

    override fun onZeroItemsLoaded() {
        load()
    }

    override fun onItemAtEndLoaded(itemAtEnd: LOCAL) {
        load(itemAtEnd)
    }

    private fun load(itemAtEnd: LOCAL? = null) = scope.launch {
        val page: Int = itemAtEnd?.let { currentPageFunction(it) }?.takeIf { it < totalPages }?.plus(1) ?: 1
        if (itemAtEnd == null || page != 1) {
            request(page)?.apply {

                loading.value = Unit
                val result = enqueue()

                result.onSuccess {
                    totalPages = totalPageFunction(it)
                    insert(it)
                }.onError { error.value = it }
            }
        }
    }
}