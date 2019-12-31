package jdr.tv.common.log

import java.io.PrintWriter
import java.io.StringWriter
import kotlin.math.min

abstract class Log {

    abstract fun log(priority: Int, tag: String, message: String)

    fun log(priority: Int, t: Throwable?, message: String?) {
        var m = message
        if (m.isNullOrEmpty()) {
            if (t == null) {
                return
            }
            m = createStackTraceString(t)
        } else if (t != null) {
            m += "\n" + createStackTraceString(t)
        }
        submitLog(priority, m)
    }

    private fun createStackTraceString(t: Throwable): String {
        val sw = StringWriter(STACK_TRACE_INITIAL_SIZE)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    private fun submitLog(priority: Int, message: String) {
        if (message.length < MAX_LOG_LENGTH) {
            log(priority, TAG, message)
        } else {
            var i = 0
            val length = message.length
            while (i < length) {
                var newline = message.indexOf('\n', i)
                newline = if (newline != -1) newline else length
                do {
                    val end = min(newline, i + MAX_LOG_LENGTH)
                    val part = message.substring(i, end)
                    log(priority, TAG, part)
                    i = end
                } while (i < newline)
                i++
            }
        }
    }

    companion object {
        private const val VERBOSE = 2
        private const val DEBUG = 3
        private const val INFO = 4
        private const val WARN = 5
        private const val ERROR = 6

        private const val TAG = "LOG"
        private const val STACK_TRACE_INITIAL_SIZE = 256
        private const val MAX_LOG_LENGTH = 4000

        private lateinit var delegate: Log

        @JvmStatic
        fun addLogger(log: Log) {
            delegate = log
        }

        @JvmStatic
        fun v(vararg any: Any?) {
            log(
                VERBOSE,
                null,
                any.joinToString(", ")
            )
        }

        @JvmStatic
        fun v(t: Throwable, message: String? = null) {
            log(VERBOSE, t, message)
        }

        @JvmStatic
        fun d(vararg any: Any?) {
            log(
                DEBUG,
                null,
                any.joinToString(", ")
            )
        }

        @JvmStatic
        fun d(t: Throwable, message: String? = null) {
            log(DEBUG, t, message)
        }

        @JvmStatic
        fun i(vararg any: Any?) {
            log(
                INFO,
                null,
                any.joinToString(", ")
            )
        }

        @JvmStatic
        fun i(t: Throwable, message: String? = null) {
            log(INFO, t, message)
        }

        @JvmStatic
        fun w(vararg any: Any?) {
            log(
                WARN,
                null,
                any.joinToString(", ")
            )
        }

        @JvmStatic
        fun w(t: Throwable, message: String? = null) {
            log(WARN, t, message)
        }

        @JvmStatic
        fun e(vararg any: Any?) {
            log(
                ERROR,
                null,
                any.joinToString(", ")
            )
        }

        @JvmStatic
        fun e(t: Throwable, message: String? = null) {
            log(ERROR, t, message)
        }

        private fun log(priority: Int, t: Throwable?, message: String?) {
            delegate.log(priority, t, message)
        }
    }
}
