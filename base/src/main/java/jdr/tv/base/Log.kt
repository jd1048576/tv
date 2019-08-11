package jdr.tv.base

import java.io.PrintWriter
import java.io.StringWriter
import kotlin.math.min

abstract class Logger {

    private val ignore = listOf(
        Logger::class.java.name,
        Log::class.java.name
    )

    protected abstract fun log(priority: Int, tag: String, message: String)

    fun log(priority: Int, t: Throwable?, message: String?) {
        val tag = createTag()
        var m = message
        if (m.isNullOrEmpty()) {
            if (t == null) {
                return
            }
            m = createStackTraceString(t)
        } else if (t != null) {
            m += "\n" + createStackTraceString(t)
        }
        submitLog(priority, tag, m)
    }

    private fun createTag(): String {
        val stackTraceElement = Throwable().stackTrace.first { it.className !in ignore }
        return ANONYMOUS_CLASS.replace(stackTraceElement.className.substringAfterLast('.'), "")
    }

    private fun createStackTraceString(t: Throwable): String {
        val sw = StringWriter(STACK_TRACE_INITIAL_SIZE)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    private fun submitLog(priority: Int, tag: String, message: String) {
        if (message.length < MAX_LOG_LENGTH) {
            log(priority, tag, message)
        } else {
            var i = 0
            val length = message.length
            while (i < length) {
                var newline = message.indexOf('\n', i)
                newline = if (newline != -1) newline else length
                do {
                    val end = min(newline, i + MAX_LOG_LENGTH)
                    val part = message.substring(i, end)
                    log(priority, tag, part)
                    i = end
                } while (i < newline)
                i++
            }
        }
    }

    companion object {
        private const val STACK_TRACE_INITIAL_SIZE = 256
        private const val MAX_LOG_LENGTH = 4000
        private val ANONYMOUS_CLASS = Regex("(\\$\\d+)+$")

        const val VERBOSE = 2
        const val DEBUG = 3
        const val INFO = 4
        const val WARN = 5
        const val ERROR = 6
    }
}

object Log {

    private lateinit var delegate: Logger

    @JvmStatic
    fun addLogger(logger: Logger) {
        delegate = logger
    }

    @JvmStatic
    fun v(vararg any: Any?) {
        log(Logger.VERBOSE, null, any.joinToString(", "))
    }

    @JvmStatic
    fun v(t: Throwable, message: String? = null) {
        log(Logger.VERBOSE, t, message)
    }

    @JvmStatic
    fun d(vararg any: Any?) {
        log(Logger.DEBUG, null, any.joinToString(", "))
    }

    @JvmStatic
    fun d(t: Throwable, message: String? = null) {
        log(Logger.DEBUG, t, message)
    }

    @JvmStatic
    fun i(vararg any: Any?) {
        log(Logger.INFO, null, any.joinToString(", "))
    }

    @JvmStatic
    fun i(t: Throwable, message: String? = null) {
        log(Logger.INFO, t, message)
    }

    @JvmStatic
    fun w(vararg any: Any?) {
        log(Logger.WARN, null, any.joinToString(", "))
    }

    @JvmStatic
    fun w(t: Throwable, message: String? = null) {
        log(Logger.WARN, t, message)
    }

    @JvmStatic
    fun e(vararg any: Any?) {
        log(Logger.ERROR, null, any.joinToString(", "))
    }

    @JvmStatic
    fun e(t: Throwable, message: String? = null) {
        log(Logger.ERROR, t, message)
    }

    @JvmStatic
    private fun log(priority: Int, t: Throwable?, message: String?) {
        delegate.log(priority, t, message)
    }
}
