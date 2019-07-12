package jdr.tv.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Dispatchers {

    private val AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors()

    val IOExecutor: ExecutorService = Executors.newFixedThreadPool(64.coerceAtLeast(AVAILABLE_PROCESSORS))
    val IO: CoroutineDispatcher = IOExecutor.asCoroutineDispatcher()
}
