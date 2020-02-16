package jdr.tv.common.log

import java.io.PrintWriter
import java.io.StringWriter

interface Log {

    fun log(level: Level, message: String)

    companion object {

        private const val STACK_TRACE_INITIAL_SIZE = 256

        private val loggerList = ArrayList<Log>()

        @JvmStatic
        fun addLogger(log: (Level, String) -> Unit) {
            loggerList.add(object : Log { override fun log(level: Level, message: String) { log(level, message) } })
        }

        @JvmStatic
        fun d(vararg any: Any?) {
            log(Debug, null, any.joinToString(", "))
        }

        @JvmStatic
        fun d(t: Throwable, message: String? = null) {
            log(Debug, t, message)
        }

        @JvmStatic
        fun i(vararg any: Any?) {
            log(Info, null, any.joinToString(", "))
        }

        @JvmStatic
        fun i(t: Throwable, message: String? = null) {
            log(Info, t, message)
        }

        @JvmStatic
        fun w(vararg any: Any?) {
            log(Warn, null, any.joinToString(", "))
        }

        @JvmStatic
        fun w(t: Throwable, message: String? = null) {
            log(Warn, t, message)
        }

        @JvmStatic
        fun e(vararg any: Any?) {
            log(Error, null, any.joinToString(", "))
        }

        @JvmStatic
        fun e(t: Throwable, message: String? = null) {
            log(Error, t, message)
        }

        private fun log(level: Level, t: Throwable?, message: String?) {
            var m = message
            if (m.isNullOrEmpty()) {
                if (t == null) {
                    return
                }
                m = createStackTraceString(t)
            } else if (t != null) {
                m += "\n" + createStackTraceString(t)
            }
            loggerList.forEach { it.log(level, m) }
        }

        private fun createStackTraceString(t: Throwable): String {
            val sw = StringWriter(STACK_TRACE_INITIAL_SIZE)
            val pw = PrintWriter(sw, false)
            t.printStackTrace(pw)
            pw.flush()
            return sw.toString()
        }
    }
}
