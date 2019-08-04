package jdr.tv.base

import java.io.PrintWriter
import java.io.StringWriter
import java.util.regex.Pattern
import kotlin.math.min

abstract class Logger {

    private val ignore = listOf(
        Logger::class.java.name,
        Log::class.java.name
    )

    private val tag: String
        get() = Throwable().stackTrace
            .first { it.className !in ignore }
            .let(::createStackElementTag)

    protected abstract fun log(priority: Int, tag: String, message: String)

    private fun createStackElementTag(element: StackTraceElement): String {
        var tag = element.className.substringAfterLast('.')
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        return tag
    }

    private fun getStackTraceString(t: Throwable): String {
        val sw = StringWriter(STACK_TRACE_INITIAL_SIZE)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    fun log(priority: Int, t: Throwable?, message: String?) {
        // Consume tag even when message is not loggable so that next message is correctly tagged.
        val tag = tag

        var m = message
        if (m.isNullOrEmpty()) {
            if (t == null) {
                return // Swallow message if it's null and there's no throwable.
            }
            m = getStackTraceString(t)
        } else if (t != null) {
            m += "\n" + getStackTraceString(t)
        }
        submitLog(priority, tag, m)
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
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")

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
        log(Logger.VERBOSE, null, any.joinToString(","))
    }

    @JvmStatic
    fun v(t: Throwable, message: String? = null) {
        log(Logger.VERBOSE, t, message)
    }

    @JvmStatic
    fun d(vararg any: Any?) {
        log(Logger.DEBUG, null, any.joinToString(","))
    }

    @JvmStatic
    fun d(t: Throwable, message: String? = null) {
        log(Logger.DEBUG, t, message)
    }

    @JvmStatic
    fun i(vararg any: Any?) {
        log(Logger.INFO, null, any.joinToString(","))
    }

    @JvmStatic
    fun i(t: Throwable, message: String? = null) {
        log(Logger.INFO, t, message)
    }

    @JvmStatic
    fun w(vararg any: Any?) {
        log(Logger.WARN, null, any.joinToString(","))
    }

    @JvmStatic
    fun w(t: Throwable, message: String? = null) {
        log(Logger.WARN, t, message)
    }

    @JvmStatic
    fun e(vararg any: Any?) {
        log(Logger.ERROR, null, any.joinToString(","))
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
