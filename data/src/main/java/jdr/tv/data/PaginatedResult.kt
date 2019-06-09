package jdr.tv.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.paging.PagedList

class PaginatedResult<T>(
    private val pagedList: LiveData<PagedList<T>>,
    private val loading: LiveData<Boolean>,
    private val exception: LiveData<Throwable>,
    val invalidate: () -> Unit
) {

    fun onLoading(lifecycleOwner: LifecycleOwner, action: (Boolean) -> Unit): PaginatedResult<T> {
        loading.observe(lifecycleOwner) { action(it) }
        return this
    }

    fun onSuccess(lifecycleOwner: LifecycleOwner, action: (PagedList<T>) -> Unit): PaginatedResult<T> {
        pagedList.observe(lifecycleOwner) { action(it) }
        return this
    }

    fun onFailure(lifecycleOwner: LifecycleOwner, action: (Throwable) -> Unit): PaginatedResult<T> {
        exception.observe(lifecycleOwner) { action(it) }
        return this
    }
}
