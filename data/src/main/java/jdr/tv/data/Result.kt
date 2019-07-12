package jdr.tv.data

sealed class Result<T> {

    companion object {
        fun <T> loading(value: T?): Result<T> = Loading(value)
        fun <T> success(value: T): Result<T> = Success(value)
        fun <T> error(exception: Exception): Result<T> = Failure(exception)
    }
}

class Loading<T>(val value: T?) : Result<T>()
class Success<T>(val value: T) : Result<T>()
class Failure<T>(val exception: Exception) : Result<T>()

inline fun <T> Result<T>.onLoading(action: (T?) -> Unit): Result<T> {
    if (this is Loading) action(this.value)
    return this
}

inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Success) action(this.value)
    return this
}

inline fun <T> Result<T>.onFailure(action: (Exception) -> Unit): Result<T> {
    if (this is Failure) action(this.exception)
    return this
}
