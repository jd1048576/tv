package jdr.tv.remote.extensions

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

fun CoroutineDispatcher.asExecutorService(): ExecutorService {
    return BasicExecutorService(asExecutor())
}

private class BasicExecutorService(private val executor: Executor) : ExecutorService, Executor by executor {
    override fun shutdown() {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> submit(p0: Callable<T>): Future<T> {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> submit(p0: Runnable, p1: T): Future<T> {
        throw UnsupportedOperationException()
    }

    override fun submit(p0: Runnable): Future<*> {
        throw UnsupportedOperationException()
    }

    override fun shutdownNow(): MutableList<Runnable> {
        throw UnsupportedOperationException()
    }

    override fun isShutdown(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun awaitTermination(p0: Long, p1: TimeUnit): Boolean {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> invokeAny(p0: MutableCollection<out Callable<T>>): T {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> invokeAny(p0: MutableCollection<out Callable<T>>, p1: Long, p2: TimeUnit): T {
        throw UnsupportedOperationException()
    }

    override fun isTerminated(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> invokeAll(p0: MutableCollection<out Callable<T>>): MutableList<Future<T>> {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> invokeAll(p0: MutableCollection<out Callable<T>>, p1: Long, p2: TimeUnit): MutableList<Future<T>> {
        throw UnsupportedOperationException()
    }
}
