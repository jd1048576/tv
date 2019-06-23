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

    fun onLoading(lifecycleOwner: LifecycleOwner, action: (Boolean) -> Unit): PaginatedResult<T> {
        loading.observe(lifecycleOwner, Observer { action(it) })
        return this
    }

    fun onSuccess(lifecycleOwner: LifecycleOwner, action: (PagedList<T>) -> Unit): PaginatedResult<T> {
        pagedList.observe(lifecycleOwner, Observer { action(it) })
        return this
    }

    fun onFailure(lifecycleOwner: LifecycleOwner, action: (Exception) -> Unit): PaginatedResult<T> {
        exception.observe(lifecycleOwner, Observer { action(it) })
        return this
    }
}
