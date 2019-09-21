package jdr.tv.remote

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import retrofit2.Response as RetrofitResponse

class Request<out T>(private val request: suspend () -> RetrofitResponse<T>) {

    companion object {
        private const val MAX_NUMBER_OF_ATTEMPTS = 3
        private const val DELAY = 1000L
        private const val TOO_MANY_REQUESTS = 429
    }

    suspend fun execute(): Response<T> = withContext(IO) {
        repeat(MAX_NUMBER_OF_ATTEMPTS) { attempt ->
            try {
                Bucket.throttle()

                val response = request()
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Response.success(response.body()!!)
                } else if (attempt == MAX_NUMBER_OF_ATTEMPTS - 1) {
                    return@withContext Response.failure<T>(HttpException(response))
                }
            } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
                if (attempt == (MAX_NUMBER_OF_ATTEMPTS - 1) || !shouldRetry(e)) {
                    return@withContext Response.failure<T>(e)
                }
                delayRetry(e, attempt)
            }
        }
        throw IllegalStateException("Illegal State in execute")
    }

    private suspend fun delayRetry(exception: Exception, attempt: Int) {
        delay(((exception as? HttpException)?.response()?.headers()?.get("Retry-After")?.toInt() ?: attempt * attempt).coerceAtLeast(1) * DELAY)
    }

    private fun shouldRetry(exception: Exception) = when (exception) {
        is HttpException -> exception.code() == TOO_MANY_REQUESTS
        is IOException -> true
        else -> false
    }
}

suspend fun <T> List<Request<T>>.execute(): Response<List<T>> = withContext(IO) {
    val responseMap = ConcurrentHashMap<Int, Response<T>>()
    mapIndexed { index, suspendFunction -> async { responseMap[index] = suspendFunction.execute() } }.awaitAll()
    val successList = ArrayList<T>()
    val failureList = ArrayList<Exception>()
    responseMap.asSequence().sortedBy { it.key }.forEach {
        when (val v = it.value) {
            is Success -> successList.add(v.value)
            is Failure -> failureList.add(v.exception)
            else -> throw IllegalStateException("Illegal State in execute")
        }
    }
    if (failureList.isNotEmpty()) {
        Response.failure(failureList[0])
    } else {
        Response.success(successList)
    }
}
