package jdr.tv.data.remote

sealed class Response<out T> {

    companion object {
        fun <T> success(value: T): Response<T> = Success(value)
        fun <T> failure(exception: Exception): Response<T> =
            Failure(exception)
    }
}

class Success<T> internal constructor(val value: T) : Response<T>()
class Failure<T> internal constructor(val exception: Exception) : Response<T>()

inline fun <T, S> Response<T>.map(transform: (T) -> S): Response<S> {
    return when (this) {
        is Success -> Response.success(transform(value))
        is Failure -> Response.failure(exception)
    }
}

inline fun <T, S> Response<T>.switchMap(transform: (T) -> Response<S>): Response<S> {
    return when (this) {
        is Success -> transform(value)
        is Failure -> Response.failure(exception)
    }
}

fun <T, S> Response<T>.merge(response: Response<S>): Response<Pair<T, S>> {
    return if (this is Success && response is Success) {
        Response.success(Pair(value, response.value))
    } else {
        Response.failure(if (this is Failure) exception else (response as Failure).exception)
    }
}

inline fun <T, S> Response<T>.mergeSwitchMap(transform: (T) -> Response<S>): Response<Pair<T, S>> {
    return when (this) {
        is Success -> {
            when (val s = transform(value)) {
                is Success -> Response.success(
                    Pair(
                        value,
                        s.value
                    )
                )
                is Failure -> Response.failure(s.exception)
            }
        }
        is Failure -> Response.failure(exception)
    }
}

inline fun <T> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (this is Success) action(this.value)
    return this
}

inline fun <T> Response<T>.onFailure(action: (Exception) -> Unit): Response<T> {
    if (this is Failure) action(this.exception)
    return this
}
