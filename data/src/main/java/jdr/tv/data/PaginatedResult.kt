package jdr.tv.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList

class PaginatedResult<T>(
    private val pagedList: LiveData<PagedList<T>>,
    private val loading: LiveData<Boolean>,
    private val exception: LiveData<Exception>,
    val invalidate: () -> Unit
) {
    fun withLifecycleOwner(lifecycleOwner: LifecycleOwner) = LifecyclePaginatedResult(this, lifecycleOwner)

    class LifecyclePaginatedResult<T> internal constructor(
        private val paginatedResult: PaginatedResult<T>,
        private val lifecycleOwner: LifecycleOwner
    ) {

        fun onLoading(action: (Boolean) -> Unit): LifecyclePaginatedResult<T> {
            paginatedResult.loading.observe(lifecycleOwner, Observer { action(it) })
            return this
        }

        fun onSuccess(action: (PagedList<T>) -> Unit): LifecyclePaginatedResult<T> {
            paginatedResult.pagedList.observe(lifecycleOwner, Observer { action(it) })
            return this
        }

        fun onFailure(action: (Exception) -> Unit): LifecyclePaginatedResult<T> {
            paginatedResult.exception.observe(lifecycleOwner, Observer { action(it) })
            return this
        }
    }
}
