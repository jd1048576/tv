package jdr.tv.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

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

inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(this.value)
    return this
}

inline fun <T> Resource<T>.onFailure(action: (Exception) -> Unit): Resource<T> {
    if (this is Resource.Failure) action(this.exception)
    return this
}

fun <T> Flow<T>.asSuccess(): Flow<Resource<T>> {
    return map { Resource.success(it) }
}

suspend inline fun <T> Flow<Resource<T>>.collectResource(crossinline action: suspend Resource<T>.() -> Unit) {
    collect(action)
}
