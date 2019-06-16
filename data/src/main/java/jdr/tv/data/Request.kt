package jdr.tv.data

import jdr.tv.base.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

class Request<T>(private val request: suspend () -> Response<T>) {

    companion object {
        private const val MAX_NUMBER_OF_ATTEMPTS = 3
    }

    suspend fun execute(): Result<T> = withContext(IO) {
        repeat(MAX_NUMBER_OF_ATTEMPTS) { attempt ->
            try {
                Bucket.throttle()

                val response = request()
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.success(response.body()!!)
                }
            } catch (e: Exception) {
                if (attempt == (MAX_NUMBER_OF_ATTEMPTS - 1) || !shouldRetry(e)) {
                    return@withContext Result.error<T>(e)
                }
                delayRetry(e, attempt)
            }
        }
        throw IllegalStateException("Illegal State in enqueue")
    }

    private suspend fun delayRetry(exception: Exception, attempt: Int) {
        delay(((exception as? HttpException)?.response()?.headers()?.get("Retry-After")?.toInt() ?: attempt * attempt).coerceAtLeast(1) * 1000L)
    }

    private fun shouldRetry(exception: Exception) = when (exception) {
        is HttpException -> exception.code() == 429
        is IOException -> true
        else -> false
    }
}

fun <T, R, S> (suspend (R, S) -> Response<T>).toRequest(r: R, s: S): Request<T> {
    return Request { this(r, s) }
}

suspend fun <T> List<Request<T>>.execute(): List<Result<T>> = withContext(IO) {
    val responseMap = ConcurrentHashMap<Int, Result<T>>()
    mapIndexed { index, suspendFunction -> async { responseMap[index] = suspendFunction.execute() } }.awaitAll()
    responseMap.asSequence().sortedBy { it.key }.map { it.value }.toList()
}

