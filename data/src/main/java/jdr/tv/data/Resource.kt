package jdr.tv.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map

sealed class Resource<T> {

    companion object {
        fun <T> loading(value: T?): Resource<T> = Loading(value)
        fun <T> success(value: T): Resource<T> = Success(value)
        fun <T> failure(exception: Exception): Resource<T> = Failure(exception)
    }

    class Loading<T> internal constructor(val value: T?) : Resource<T>()
    class Success<T> internal constructor(val value: T) : Resource<T>()
    class Failure<T> internal constructor(val exception: Exception) : Resource<T>()
}

inline fun <X, Y> Resource<X>.map(block: (X) -> Y): Resource<Y> {
    return when (this) {
        is Resource.Loading -> Resource.loading(value?.run(block))
        is Resource.Success -> Resource.success(block(value))
        is Resource.Failure -> Resource.failure(exception)
    }
}

inline fun <T> Resource<T>.onLoading(action: (T?) -> Unit): Resource<T> {
    if (this is Resource.Loading) action(this.value)
    return this
}

fun <T> LiveData<T>.asLoading(): LiveData<Resource<T>> {
    return map { Resource.loading(it) }
}

inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(this.value)
    return this
}

fun <T> LiveData<T>.asSuccess(): LiveData<Resource<T>> {
    return map { Resource.success(it) }
}

inline fun <T> Resource<T>.onFailure(action: (Exception) -> Unit): Resource<T> {
    if (this is Resource.Failure) action(this.exception)
    return this
}
