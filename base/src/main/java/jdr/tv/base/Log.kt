@file:Suppress("TooManyFunctions")

package jdr.tv.base

object Log : Logger {

    private lateinit var delegate: Logger

    @JvmStatic
    fun initialize(logger: Logger) {
        delegate = logger
    }

    override fun v(message: String, vararg args: Any) {
        delegate.v(message, args)
    }

    override fun v(t: Throwable, message: String, vararg args: Any) {
        delegate.v(t, message, args)
    }

    override fun v(t: Throwable) {
        delegate.v(t)
    }

    override fun d(message: String, vararg args: Any) {
        delegate.v(message, args)
    }

    override fun d(t: Throwable, message: String, vararg args: Any) {
        delegate.d(t, message, args)
    }

    override fun d(t: Throwable) {
        delegate.d(t)
    }

    override fun i(message: String, vararg args: Any) {
        delegate.i(message, args)
    }

    override fun i(t: Throwable, message: String, vararg args: Any) {
        delegate.i(t, message, args)
    }

    override fun i(t: Throwable) {
        delegate.i(t)
    }

    override fun w(message: String, vararg args: Any) {
        delegate.w(message, args)
    }

    override fun w(t: Throwable, message: String, vararg args: Any) {
        delegate.w(t, message, args)
    }

    override fun w(t: Throwable) {
        delegate.w(t)
    }

    override fun e(message: String, vararg args: Any) {
        delegate.e(message, args)
    }

    override fun e(t: Throwable, message: String, vararg args: Any) {
        delegate.e(t, message, args)
    }

    override fun e(t: Throwable) {
        delegate.e(t)
    }

    override fun wtf(message: String, vararg args: Any) {
        delegate.wtf(message, args)
    }

    override fun wtf(t: Throwable, message: String, vararg args: Any) {
        delegate.wtf(t, message, args)
    }

    override fun wtf(t: Throwable) {
        delegate.wtf(t)
    }

    override fun log(priority: Int, message: String, vararg args: Any) {
        delegate.log(priority, message, args)
    }

    override fun log(priority: Int, t: Throwable, message: String, vararg args: Any) {
        delegate.log(priority, t, message, args)
    }

    override fun log(priority: Int, t: Throwable) {
        delegate.log(priority, t)
    }
}

interface Logger {

    fun v(message: String, vararg args: Any)

    fun v(t: Throwable, message: String, vararg args: Any)

    fun v(t: Throwable)

    fun d(message: String, vararg args: Any)

    fun d(t: Throwable, message: String, vararg args: Any)

    fun d(t: Throwable)

    fun i(message: String, vararg args: Any)

    fun i(t: Throwable, message: String, vararg args: Any)

    fun i(t: Throwable)

    fun w(message: String, vararg args: Any)

    fun w(t: Throwable, message: String, vararg args: Any)

    fun w(t: Throwable)

    fun e(message: String, vararg args: Any)

    fun e(t: Throwable, message: String, vararg args: Any)

    fun e(t: Throwable)

    fun wtf(message: String, vararg args: Any)

    fun wtf(t: Throwable, message: String, vararg args: Any)

    fun wtf(t: Throwable)

    fun log(priority: Int, message: String, vararg args: Any)

    fun log(priority: Int, t: Throwable, message: String, vararg args: Any)

    fun log(priority: Int, t: Throwable)
}
