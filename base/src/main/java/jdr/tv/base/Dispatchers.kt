package jdr.tv.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Dispatchers {

    private const val MAX_THREADS = 64

    private val AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors()

    val IOExecutor: ExecutorService = Executors.newFixedThreadPool(MAX_THREADS.coerceAtLeast(AVAILABLE_PROCESSORS))
    val IO: CoroutineDispatcher = IOExecutor.asCoroutineDispatcher()
}
